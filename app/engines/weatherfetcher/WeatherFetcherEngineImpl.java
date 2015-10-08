package engines.weatherfetcher;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import play.Logger;
import play.libs.Json;
import services.conf.ConfigurationService;
import services.http.HttpService;
import services.weather.WeatherService;
import api.objects.City;
import api.objects.Speed;
import api.objects.Temperature;
import api.objects.WeatherDay;
import api.objects.WeatherDayConditions;

import com.google.inject.Inject;

import engines.pool.PoolEngine;
import engines.weatherfetcher.WorldWeatherOnlineResponse.WorldWeatherOnlineResponseWeather;

/**
 * An engine that periodically fetch weather forecast
 * @author Jerome Baudoux
 */
public class WeatherFetcherEngineImpl implements WeatherFetcherEngine {

	protected static final String API_KEY = "WWO_API_KEY";
	protected static final String API_URL = "http://api.worldweatheronline.com/free/v1/weather.ashx?q={C}&format=json&num_of_days=5&cc=no&key={K}";
	
	protected final long DELAY_BETWEEN_REFRESH = 1000 * 60 * 60; // 1 hour
	protected final long DELAY_BETWEEN_TWO_GET = 1000 * 5; // 5 seconds
	
	protected final WeatherService weatherService;
	protected final HttpService http;
	protected final WeatherService weather;
	protected final ConfigurationService config;
	protected final PoolEngine poolEngine;

	protected final String apiKey;
	protected final Map<String, WeatherDayConditions> conditionsMap;
	
	/**
	 * Constructor
	 * @param http http service
	 * @param weather weather service
	 * @param poolEngine poolEngine service
	 */
	@Inject
	public WeatherFetcherEngineImpl(WeatherService weatherService, HttpService http, 
			WeatherService weather, ConfigurationService config, PoolEngine poolEngine) {
		this.weatherService = weatherService;
		this.http = http;
		this.weather = weather;
		this.config = config;
		this.poolEngine = poolEngine;
		this.apiKey = config.getEnvVariable(API_KEY, "API_KEY_NOT_LOADED");
		this.conditionsMap = new HashMap<>();
	}

	/**
	 * on Startup
	 */
	@Override
	public void start() {
		
		fillConditionMap();
		
		// Schedule this task
		this.poolEngine.scheduleTask(()->run(), 0, DELAY_BETWEEN_REFRESH);
	}

	/**
	 * on Shutdown
	 */
	@Override
	public void stop() {

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
		fillConditionMap(WeatherDayConditions.LIGHT_SLEET, "182", "317");
		fillConditionMap(WeatherDayConditions.LIGHT_ICE_PELLET, "362", "374");
		fillConditionMap(WeatherDayConditions.LIGHT_FROZEN_RAIN_OR_DRIZZLE, "185", "311", "260");
		fillConditionMap(WeatherDayConditions.LIGHT_RAIN_WITH_THUNDER, "386");
		fillConditionMap(WeatherDayConditions.LIGHT_SNOW_WITH_THUNDER, "392");
		fillConditionMap(WeatherDayConditions.MODERATE_RAIN, "302", "299", "356");
		fillConditionMap(WeatherDayConditions.MODERATE_SNOW, "332", "329", "227");
		fillConditionMap(WeatherDayConditions.MODERATE_SLEET, "320", "365");
		fillConditionMap(WeatherDayConditions.MODERATE_ICE_PELLET, "350");
		fillConditionMap(WeatherDayConditions.MODERATE_FROZEN_RAIN_OR_DRIZZLE, "314", "281");
		fillConditionMap(WeatherDayConditions.MODERATE_OR_HEAVY_RAIN_WITH_THUNDER, "389");
		fillConditionMap(WeatherDayConditions.MODERATE_OR_HEAVY_SNOW_WITH_THUNDER, "395");
		fillConditionMap(WeatherDayConditions.HEAVY_RAIN, "308", "359", "305");
		fillConditionMap(WeatherDayConditions.HEAVY_SNOW, "230", "338", "371", "335");
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
	
	protected Integer parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Throwable t) {
			Logger.error("Cannot parse value: " + value + " into an Integer");
			return null;
		}
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
					.setWindDirection(Integer.parseInt(weather.getWinddirDegree()))
					.setTemperatureMax(new Temperature()
						.setCelsius(parseInt(weather.getTempMaxC()))
						.setFahrenheit(parseInt(weather.getTempMaxF())))
					.setTemperatureMin(new Temperature()
						.setCelsius(parseInt(weather.getTempMinC()))
						.setFahrenheit(parseInt(weather.getTempMinF())))
					.setWindSpeed(new Speed()
						.setKmph(parseInt(weather.getWindspeedKmph()))
						.setMph(parseInt(weather.getWindspeedMiles())))
					.setConditions(makeWeatherDayConditions(weather.getWeatherCode()));
			} catch(Throwable t) {
				Logger.error("Error while tranforming forecast for the city: " + city, t);
				return new WeatherDay();
			}
		}).collect(Collectors.toList());
	}


	private void run() {
		try {
			// Need a key
			if(this.apiKey==null) {
				Logger.error("No API Key configured");
			}
			
			// For every city, do a query
			for(City cityObject: this.weather.getCities()) {
				
				final String city = cityObject.getNameAndCountry();

				// URL for the query
				final String url = API_URL
						.replace("{C}", URLEncoder.encode(city, "UTF-8"))
						.replace("{K}", URLEncoder.encode(this.apiKey, "UTF-8"));
				
				// Fetch forecast
				this.http.get(url, (String body, int status) -> {
					
					// Everything went fine
					try {
						// Parse into JSON
						WorldWeatherOnlineResponse response = Json.fromJson(Json.parse(body), WorldWeatherOnlineResponse.class);
						Logger.trace("Forecast for " + city + " fetched for the next: " + response.getData().getWeather().length + " days");
						
						// Transform into an API object
						List<WeatherDay> apiObjects = makeWeatherDays(cityObject, response);
						Logger.trace("Forecast for " + city + " transformed for the next: " + apiObjects.size() + " days");

						// Send to History Database and Forecast cache
						this.weatherService.addForecast(apiObjects);
						
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
