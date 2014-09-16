package engine.pingengine;

import java.util.Timer;
import java.util.TimerTask;

import com.google.inject.Singleton;

import akka.actor.ActorSystem;
import play.Logger;
import play.Play;
import play.api.libs.ws.WS;
import play.api.libs.ws.WSResponse;
import scala.concurrent.*;
import scala.runtime.AbstractFunction1;
import scala.runtime.AbstractPartialFunction;

/**
 * An engine that pings the interface every few minutes, 
 * And writes the result in the log
 * @author Jerome Baudoux
 */
@Singleton
public class PingEngineImpl implements PingEngine {
	
	public static final long DELAY = 1000 * 60 * 5; // 5 minutes
	
	protected ExecutionContext context;

	protected TimerTask timerTask;
	protected Timer timer;
	
	protected String url;

	/**
	 * on Startup
	 */
	@Override
	public void start() {
		this.context = ActorSystem.create().dispatcher().prepare();
		this.url = Play.application().configuration().getString("heroku.http.url");
		this.timerTask = new PingTask();
		this.timer = new Timer(true);
		this.timer.scheduleAtFixedRate(timerTask, 0, DELAY);
	}

	/**
	 * on Shutdown
	 */
	@Override
	public void stop() {
		this.timer.cancel();
	}
	
	/**
	 * The actual task
	 * @author Jerome Baudoux
	 */
	public class PingTask extends TimerTask {
		@Override
		public void run() {
			
			try {
				// For now only send queries, do not fetch result
				Future<WSResponse> future = WS.url(
					PingEngineImpl.this.url, 
					Play.application().getWrappedApplication()
				).get();

				// Checks http status
				future.onSuccess(new AbstractPartialFunction<WSResponse, Void>() {
					@Override
					public boolean isDefinedAt(WSResponse response) {
						if(response.status()>400) {
							Logger.error("Error, cannot reach application");
						}
						return false;
					}
				}, PingEngineImpl.this.context);
				
				// CHecks connection error
				future.onFailure(new AbstractPartialFunction<Throwable, Void>() {
					@Override
					public boolean isDefinedAt(Throwable error) {
						Logger.error("Error, cannot reach application", error);
						return false;
					}
				}, PingEngineImpl.this.context);
				
			} catch (Throwable t) {
				Logger.info("Error, cannot reach application", t);
			}
		}
	}
}
