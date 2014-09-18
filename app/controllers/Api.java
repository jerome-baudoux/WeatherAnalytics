package controllers;

import com.google.inject.Singleton;

import api.ApiResultConstants;
import api.SimpleApiResponse;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * A controller for all public API
 * @author Jerome Baudoux
 */
@Singleton
public class Api extends Controller {

	/**
	 * A simple ping request
	 * @return Pong
	 */
	public Result ping() {
		return ok(
				new SimpleApiResponse()
				.setResult(ApiResultConstants.SUCCESS)
				.toJSON()
		);
	}

	/**
	 * When an unknown API was called
	 * @param name name of the api
	 * @return 404 error
	 */
	public Result apiNotFound(String name) {
		return notFound(
				new SimpleApiResponse()
					.setResult(ApiResultConstants.ERROR_API_NOT_FOUND)
					.setMessage("No API found for the name: " + name)
					.toJSON()
		);
	}
	
	/**
	 * For debug only
	 * @return sends a message
	 */
	public Promise<Result> longOperation(int time) {

	    Promise<String> promise = Promise.promise(() -> {
	    	Thread.sleep(time);
	    	return "Current time: " + System.currentTimeMillis();
	    });     
	    
	    return promise.map((String message) -> {
	    	return ok(
					new SimpleApiResponse()
					.setResult(ApiResultConstants.SUCCESS)
					.setMessage(message)
					.toJSON()
			);
	    });
		
	}
}
