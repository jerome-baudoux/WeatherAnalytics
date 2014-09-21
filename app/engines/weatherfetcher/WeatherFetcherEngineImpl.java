package engines.weatherfetcher;

import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import play.Logger;
import play.libs.Json;
import services.http.HttpService;
import services.weather.WeatherService;

import com.google.inject.Inject;

/**
 * An engine that periodically fetch weather forecast
 * @author Jerome Baudoux
 */
public class WeatherFetcherEngineImpl implements WeatherFetcherEngine {

	protected String API_KEY = "WWO_API_KEY";
	protected String API_URL = "http://api.worldweatheronline.com/free/v1/weather.ashx?q={C}&format=json&num_of_days=5&cc=no&key={K}";
	
	protected long DELAY_BETWEEN_REFRESH = 1000 * 60 * 60; // 1 hour
	protected long DELAY_BETWEEN_TWO_GET = 1000 * 5; // 5 seconds
	
	protected HttpService http;
	protected WeatherService weather;

	protected TimerTask timerTask;
	protected Timer timer;
	
	protected String apiKey = "<NOT_LOADED>";
	
	/**
	 * Constructor
	 * @param http http service
	 * @param cypher cypher service
	 */
	@Inject
	public WeatherFetcherEngineImpl(HttpService http, WeatherService weather) {
		this.http = http;
		this.weather = weather;
		this.apiKey = System.getenv().get("WWO_API_KEY");
	}

	/**
	 * on Startup
	 */
	@Override
	public void start() {
		this.timerTask = new FetchTask();
		this.timer = new Timer(true);
		this.timer.scheduleAtFixedRate(timerTask, 0, DELAY_BETWEEN_REFRESH);
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
	public class FetchTask extends TimerTask {
		@Override
		public void run() {
			try {
				// For every city, do a query
				for(String city: WeatherFetcherEngineImpl.this.weather.getCities()) {
					
					try {
						// URL for the query
						String url = API_URL
								.replace("{C}", URLEncoder.encode(city, "UTF-8"))
								.replace("{K}", URLEncoder.encode(WeatherFetcherEngineImpl.this.apiKey, "UTF-8"));
						
						// Fetch forecast
						WeatherFetcherEngineImpl.this.http.get(url, (String body, int status) -> {
							
							// Everything went fine
							try {
								// Parse into JSON
								WorldWeatherOnlineResponse response = Json.fromJson(Json.parse(body), WorldWeatherOnlineResponse.class);
								Logger.trace("Forecast for " + city + " fetched for the next: " + response.getData().getWeather().length + " days");
								// TODO -> Transform into a better looking object
								// TODO -> Send to History Database and Forecast cache
							} catch (Throwable t) {
								Logger.error("Error while parsing forecast for the city: " + city, t);
							}
							
						}, (String body, int status, Throwable t) -> {
							
							// An error occurred
							Logger.error("Error while fetching forecast for the city: " + city);
						});
						
					} catch (Throwable t) {
						Logger.error("Error while fetching forecast for the city: " + city, t);
					}
					
					// Wait a little
					try {
						Thread.sleep(DELAY_BETWEEN_TWO_GET);
					} catch (InterruptedException e) {
						// Silent
					}
				}
			} catch (Throwable t) {
				Logger.error("Fatal error, cannot fetch forecast", t);
			}
		}
	}
}
