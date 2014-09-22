package engines.weatherfetcher;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import play.Logger;
import play.libs.Json;
import services.http.HttpService;
import services.weather.WeatherService;
import api.objects.City;
import api.objects.WeatherDay;
import api.objects.WeatherDayConditions;

import com.google.inject.Inject;

import engines.forecastholder.ForecastHolderEngine;
import engines.weatherfetcher.WorldWeatherOnlineResponse.WorldWeatherOnlineResponseWeather;

/**
 * An engine that periodically fetch weather forecast
 * @author Jerome Baudoux
 */
public class WeatherFetcherEngineImpl implements WeatherFetcherEngine {

	protected static final String API_KEY = "WWO_API_KEY";
	protected static final String API_URL = "http://api.worldweatheronline.com/free/v1/weather.ashx?q={C}&format=json&num_of_days=5&cc=no&key={K}";
	
	protected long DELAY_BETWEEN_REFRESH = 1000 * 60 * 60; // 1 hour
	protected long DELAY_BETWEEN_TWO_GET = 1000 * 5; // 5 seconds
	
	protected ForecastHolderEngine forecastHolderEngine;
	
	protected HttpService http;
	protected WeatherService weather;

	protected TimerTask timerTask;
	protected Timer timer;
	
	protected String apiKey = "<NOT_LOADED>";
	protected Map<String, WeatherDayConditions> conditionsMap;
	
	/**
	 * Constructor
	 * @param http http service
	 * @param cypher cypher service
	 */
	@Inject
	public WeatherFetcherEngineImpl(ForecastHolderEngine forecastHolderEngine, HttpService http, WeatherService weather) {
		this.forecastHolderEngine = forecastHolderEngine;
		this.http = http;
		this.weather = weather;
		this.apiKey = System.getenv().get("WWO_API_KEY");
		this.conditionsMap = new HashMap<>();
	}

	/**
	 * on Startup
	 */
	@Override
	public void start() {
		this.timerTask = new FetchTask();
		this.timer = new Timer(true);
		this.timer.scheduleAtFixedRate(timerTask, 0, DELAY_BETWEEN_REFRESH);
		fillConditionMap();
	}

	/**
	 * on Shutdown
	 */
	@Override
	public void stop() {
		this.timer.cancel();
	}

	/**
	 * Fill the mapping for condition codes
	 */
	protected void fillConditionMap() {
		fillConditionMap(WeatherDayConditions.SUNNY, "113");
		fillConditionMap(WeatherDayConditions.CLOUDY, "116", "122", "119");
		fillConditionMap(WeatherDayConditions.MIST, "143", "248");
		fillConditionMap(WeatherDayConditions.THUNDER, "200");
		fillConditionMap(WeatherDayConditions.DRIZZLE, "263", "266");
		fillConditionMap(WeatherDayConditions.LIGHT_RAIN, "176", "293", "353", "296");
		fillConditionMap(WeatherDayConditions.LIGHT_SNOW, "179", "323", "368", "326");
		fillConditionMap(WeatherDayConditions.LIGHT_ICE_PELLET, "182", "362", "317", "374");
		fillConditionMap(WeatherDayConditions.LIGHT_FROZEN_RAIN_OR_DRIZZLE, "185", "311", "260");
		fillConditionMap(WeatherDayConditions.LIGHT_RAIN_WITH_THUNDER, "386");
		fillConditionMap(WeatherDayConditions.LIGHT_SNOW_WITH_THUNDER, "392");
		fillConditionMap(WeatherDayConditions.MODERATE_RAIN, "302", "299", "356");
		fillConditionMap(WeatherDayConditions.MODERATE_SNOW, "332", "329");
		fillConditionMap(WeatherDayConditions.MODERATE_ICE_PELLET, "320", "365", "350");
		fillConditionMap(WeatherDayConditions.MODERATE_FROZEN_RAIN_OR_DRIZZLE, "314", "281");
		fillConditionMap(WeatherDayConditions.MODERATE_OR_HEAVY_RAIN_WITH_THUNDER, "395");
		fillConditionMap(WeatherDayConditions.MODERATE_OR_HEAVY_SNOW_WITH_THUNDER, "389");
		fillConditionMap(WeatherDayConditions.HEAVY_RAIN, "308", "359", "305");
		fillConditionMap(WeatherDayConditions.HEAVY_SNOW, "230", "338", "371", "227", "335");
		fillConditionMap(WeatherDayConditions.HEAVY_ICE_PELLET, "377");
		fillConditionMap(WeatherDayConditions.HEAVY_FROZEN_RAIN_OR_DRIZZLE, "284");
	}
	
	/**
	 * Fill the mapping for condition codes
	 * @param condition condition
	 * @param codes codes
	 */
	protected void fillConditionMap(WeatherDayConditions condition, String ... codes) {
		for(String code: codes) {
			this.conditionsMap.put(code, condition);
		}
	}
	
	/**
	 * Build a WeatherDayConditions out of a String code
	 * @param code String code
	 * @return WeatherDayConditions
	 */
	protected WeatherDayConditions makeWeatherDayConditions(String code) {
		WeatherDayConditions condition = this.conditionsMap.get(code);
		if(condition==null) {
			condition = WeatherDayConditions.UNKNOWN;
		}
		return condition;
	}
	
	/**
	 * Transforms a WorldWeatherOnlineResponse into a List<WeatherDay>
	 * Use of map/collect so that we don't have to loop and create a new object
	 * @param response WorldWeatherOnlineResponse
	 * @return List<WeatherDay>
	 */
	protected List<WeatherDay> makeWeatherDays(City city, WorldWeatherOnlineResponse response) {
		return Arrays.asList(response.getData().getWeather()).stream().map((WorldWeatherOnlineResponseWeather weather)-> {
			try {
				return new WeatherDay()
					.setCity(city)
					.setDate(weather.getDate())
					.setPrecipitation(Double.parseDouble(weather.getPrecipMM()))
					.setTemperatureMax(Double.parseDouble(weather.getTempMaxC()))
					.setTemperatureMin(Double.parseDouble(weather.getTempMinC()))
					.setWindDirection(Double.parseDouble(weather.getWinddirDegree()))
					.setWindSpeed(Double.parseDouble(weather.getWindspeedKmph()))
					.setConditions(makeWeatherDayConditions(weather.getWeatherCode()));
			} catch(Throwable t) {
				Logger.error("Error while tranforming forecast for the city: " + city, t);
				return new WeatherDay();
			}
		}).collect(Collectors.toList());
	}

	/**
	 * The actual task
	 * @author Jerome Baudoux
	 */
	protected class FetchTask extends TimerTask {
		@Override
		public void run() {
			try {
				// Need a key
				if(WeatherFetcherEngineImpl.this.apiKey==null) {
					Logger.error("No API Key configured");
				}
				
				// For every city, do a query
				for(City cityObject: WeatherFetcherEngineImpl.this.weather.getCities()) {
					
					final String city = cityObject.getNameAndCountry();

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
							
							// Transform into an API object
							List<WeatherDay> apiObjects = makeWeatherDays(cityObject, response);
							Logger.trace("Forecast for " + city + " transformed for the next: " + apiObjects.size() + " days");
							
							// TODO -> Send to History Database and Forecast cache
							forecastHolderEngine.addForecast(apiObjects);
							
						} catch (Throwable t) {
							Logger.error("Error while parsing forecast for the city: " + city, t);
						}
						
					}, (String body, int status, Throwable t) -> {
						
						// An error occurred
						Logger.error("Error while fetching forecast for the city: " + city);
					});

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
