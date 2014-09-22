package engines.ping;

import java.util.Timer;
import java.util.TimerTask;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import play.Logger;
import play.Play;
import services.http.HttpService;

/**
 * An engine that pings the interface every few minutes, 
 * And writes the result in the log
 * @author Jerome Baudoux
 */
@Singleton
public class PingEngineImpl implements PingEngine {
	
	protected static final long DELAY = 1000 * 60 * 5; // 5 minutes
	
	protected HttpService http;

	protected TimerTask timerTask;
	protected Timer timer;
	
	protected String url;
	
	/**
	 * Constructor
	 * @param http http service
	 */
	@Inject
	public PingEngineImpl(HttpService http) {
		this.http = http;
	}

	/**
	 * on Startup
	 */
	@Override
	public void start() {
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
			PingEngineImpl.this.http.get(PingEngineImpl.this.url, (String body, int status) -> {
				Logger.trace("Application can be reached");
			}, (String body, int status, Throwable t) -> {
				Logger.error("Error, cannot reach application");
			});
		}
	}
}
