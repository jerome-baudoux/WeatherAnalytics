package services.http;

import com.google.inject.Singleton;

import akka.actor.ActorSystem;
import play.Logger;
import play.Play;
import play.api.libs.ws.WS;
import play.api.libs.ws.WSResponse;
import scala.concurrent.*;
import scala.runtime.AbstractPartialFunction;

/**
 * A service that is able to do HTTP request and provide the result asynchronously
 * @author Jerome Baudoux
 *
 */
@Singleton
public class HttpServiceImpl implements HttpService {

	/**
	 * Akka execution context
	 */
	protected ExecutionContext context;
	
	/**
	 * Constructor
	 */
	public HttpServiceImpl() {
		this.context = ActorSystem.create().dispatcher().prepare();
	}
	
	/**
	 * Simple GET
	 * @param url URL to query
	 * @param success called in case of success
	 * @param error called in case of error
	 */
	@Override
	public void get(String url, Success success, Error error) {
		
		Logger.trace("Executing GET request: " + url);
		
		try {
			// For now only send queries, do not fetch result
			Future<WSResponse> future = WS.url(url, Play.application().getWrappedApplication()).get();

			// Checks http status
			future.onSuccess(new AbstractPartialFunction<WSResponse, Void>() {
				@Override
				public boolean isDefinedAt(WSResponse response) {
					Logger.trace("Result for GET request: " + url + " is: " + response.status());
					if(response.status()>400) {
						error.error(response.body(), response.status(), null);
					}
					success.success(response.body(), response.status());
					return false;
				}
			}, HttpServiceImpl.this.context);
			
			// CHecks connection error
			future.onFailure(new AbstractPartialFunction<Throwable, Void>() {
				@Override
				public boolean isDefinedAt(Throwable t) {
					Logger.trace("Error for GET request: " + url, t);
					error.error(null, 0, t);
					return false;
				}
			}, HttpServiceImpl.this.context);
			
		} catch (Throwable t) {
			Logger.trace("Error for GET request: " + url, t);
			error.error(null, 0, t);
		}
	}
}
