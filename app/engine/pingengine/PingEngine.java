package engine.pingengine;

import java.util.Timer;
import java.util.TimerTask;

import akka.actor.ActorSystem;
import play.Logger;
import play.Play;
import play.api.libs.ws.WS;
import play.api.libs.ws.WSResponse;
import engine.Engine;
import scala.concurrent.*;
import scala.runtime.AbstractFunction1;
import scala.runtime.AbstractPartialFunction;

/**
 * An engine that pings the interface every few minutes, 
 * And writes the result in the log
 * @author Jerome Baudoux
 */
public class PingEngine implements Engine {
	
	public static final long DELAY = 1000 * 60 * 5; // 5 minutes
	
	protected ExecutionContext context;

	protected TimerTask timerTask;
	protected Timer timer;
	
	protected String url;
	
	public class PingTask extends TimerTask {
		@Override
		public void run() {
			
			try {
				// For now only send queries, do not fetch result
				Future<WSResponse> future = WS.url(
					PingEngine.this.url, 
					Play.application().getWrappedApplication()
				).get();

				// Checks http status
				future.map(new AbstractFunction1<WSResponse, Void>() {
					@Override
					public Void apply(WSResponse response) {
						if(response.status()>400) {
							Logger.error("Error, cannot reach application");
						}
						return null;
					}
				}, PingEngine.this.context);
				
				// CHecks connection error
				future.onFailure(new AbstractPartialFunction<Throwable, Void>() {
					@Override
					public boolean isDefinedAt(Throwable error) {
						Logger.error("Error, cannot reach application", error);
						return false;
					}
				}, PingEngine.this.context);
				
			} catch (Throwable t) {
				Logger.info("Error, cannot reach application", t);
			}
		}
	}

	@Override
	public void start() {
		this.context = ActorSystem.create().dispatcher().prepare();
		this.url = Play.application().configuration().getString("heroku.http.url");
		this.timerTask = new PingTask();
		this.timer = new Timer(true);
		this.timer.scheduleAtFixedRate(timerTask, 0, DELAY);
	}

	@Override
	public void stop() {
		this.timer.cancel();
	}
}
