package engines.ping;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import engines.pool.PoolEngine;
import play.Logger;
import services.conf.ConfigurationService;
import services.http.HttpService;

/**
 * An engine that pings the interface every few minutes, 
 * And writes the result in the log
 * @author Jerome Baudoux
 */
@Singleton
public class PingEngineImpl implements PingEngine {
	
	protected static final long DELAY_DEFAULT_MINUTES = 5; // 5 minutes
	
	protected final HttpService http;
	protected final ConfigurationService configuration;
	
	protected final PoolEngine poolEngine;

	protected String url;
	protected long delay;
	
	/**
	 * Constructor
	 * @param http http service
	 */
	@Inject
	public PingEngineImpl(HttpService http, PoolEngine poolEngine, ConfigurationService configuration) {
		this.http = http;
		this.configuration = configuration;
		this.poolEngine = poolEngine;
	}

	/**
	 * on Startup
	 */
	@Override
	public void start() {
		
		// Fetch configuration
		this.url = this.configuration.getString(ConfigurationService.PING_URL, "NO_URL");
		this.delay = this.configuration.getLong(ConfigurationService.PING_FREQUENCY, DELAY_DEFAULT_MINUTES) * 1000 * 60;

		// Schedule tasks
		this.poolEngine.scheduleTask(() -> {
			this.http.get(this.url, (String body, int status) -> {
				Logger.trace("Application can be reached");
			}, (String body, int status, Throwable t) -> {
				Logger.error("Error, cannot reach application");
			});
		}, 0, this.delay);
	}

	/**
	 * on Shutdown
	 */
	@Override
	public void stop() {
		//this.timer.cancel();
	}
}
