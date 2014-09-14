package engine.pingengine;

import java.util.Timer;
import java.util.TimerTask;

import play.Logger;
import play.Play;
import play.api.libs.ws.WS;
import play.api.libs.ws.WSClient;
import engine.Engine;

/**
 * An engine that pings the interface every few minutes, 
 * And writes the result in the log
 * @author Jerome Baudoux
 */
public class PingEngine implements Engine {
	
	public static final long DELAY = 1000 * 60 * 5; // 5 minutes

	protected TimerTask timerTask;
	protected Timer timer;
	
	protected String url;
	
	public class PingTask extends TimerTask {
		@Override
		public void run() {
			// For now only send queries, do not fetch result
			WS.url(
				PingEngine.this.url, 
				Play.application().getWrappedApplication()
			).get();
			Logger.info("Contacting website...");
		}
	}

	@Override
	public void start() {
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
