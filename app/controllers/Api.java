package controllers;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import api.objects.City;
import api.objects.WeatherDay;
import api.response.ApiResponse;
import api.response.ApiResultCode;
import api.response.SimpleApiResponse;
import api.response.weather.CitiesResponse;
import api.response.weather.ForecastResponse;
import api.response.weather.HistoryResponse;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;
import services.weather.WeatherService;
import services.weather.WeatherService.HistoryData;
import services.weather.WeatherService.NoSuchCityException;

/**
 * A controller for all public API
 * @author Jerome Baudoux
 */
@Singleton
public class Api extends Controller {
	
	private static final String CONTENT_TYPE_JSON = "application/json";
	
	/**
	 * Constructor
	 */
	@Inject
	public Api(WeatherService weatherService) {
		this.weatherService = weatherService;
	}
	
	protected WeatherService weatherService;

	/**
	 * A simple ping request
	 * @return Ok message
	 */
	public Result ping() {
		return okJson(new SimpleApiResponse()
			.setResult(ApiResultCode.SUCCESS.getCode()));
	}

	/**
	 * When an unknown API was called
	 * @param name name of the api
	 * @return 404 error
	 */
	public Result apiNotFound(String name) {
		return notFoundJson(new SimpleApiResponse()
			.setResult(ApiResultCode.ERROR_API_NOT_FOUND.getCode())
			.setMessage("No API found for the name: " + name));
	}

	/**
	 * When an api with wrong parameters was called
	 * @param name name of the api
	 * @return 500 error
	 */
	public Result apiMissingParameters(String name, String parameters) {
		return internalServerErrorJson(new SimpleApiResponse()
			.setResult(ApiResultCode.ERROR_PARAMETER_MISSING.getCode())
			.setMessage("The "+name+" API was called with wrong parameters" 
							+ ((parameters!=null) ? (": "+parameters) : "")));
	}
	
	/**
	 * Fetch the list of supported cities
	 * @return a list of cities
	 */
	public Promise<Result> cities() {
		
		// Request / Result
		return Promise.promise(() -> (Result) okJson(new CitiesResponse()	
				.setResult(ApiResultCode.SUCCESS.getCode())
				.setCities(this.weatherService.getCities()))
		// Error
		).recoverWith(Api::getError);
	}
	
	/**
	 * Gets the forecast for the specified city
	 * @return
	 */
	public Promise<Result> forecast(String city, String country) {
		
		// Request
		return Promise.promise(() -> this.weatherService.getForecast(new City(city, country))
				
		// Result
		).map((List<WeatherDay> days) -> (Result) okJson(new ForecastResponse()
				.setResult(ApiResultCode.SUCCESS.getCode())
				.setForecast(days))
				
		// Error
		).recoverWith(Api::getError);
	}
	
	/**
	 * Gets the forecast for the specified city
	 * @return
	 */
	public Promise<Result> history(String city, String country, String from, String to) {

		// Request
		return Promise.promise(() -> this.weatherService.getHistory(new City(city, country), from, to)
				
		// Result
		).map((HistoryData data) -> (Result) okJson(new HistoryResponse()
				.setResult(ApiResultCode.SUCCESS.getCode())
				.setHistory(data.getHistory())
				.setConsolidation(data.getConsolidation()))
				
		// Error
		).recoverWith(Api::getError);
	}
	
	/*
	 * Internal
	 */
	
	/**
	 * Return an error message as a promise
	 * @param t throwable
	 * @return error message
	 */
	protected static Promise<Result> getError(Throwable t) {
		return Promise.promise(() -> (Result) internalServerErrorJson(new SimpleApiResponse()
				.setResult(getCodeForException(t))
				.setMessage(t.getMessage())
				.setError(t))
		);
	}
	
	/**
	 * Returns a pretty print JSON result
	 * @param response JSON
	 * @return result
	 */
	protected static Result okJson(ApiResponse<?> response) {
		try {
			return ok(response.toPrettyJSON()).as(CONTENT_TYPE_JSON);
		} catch (Throwable t) {
			return ok(response.toJSON());
		}
	}
	
	/**
	 * Returns a pretty print JSON result
	 * @param response JSON
	 * @return result
	 */
	protected static Result internalServerErrorJson(ApiResponse<?> response) {
		try {
			return internalServerError(response.toPrettyJSON()).as(CONTENT_TYPE_JSON);
		} catch (Throwable t) {
			return internalServerError(response.toJSON());
		}
	}
	
	/**
	 * Returns a pretty print JSON result
	 * @param response JSON
	 * @return result
	 */
	protected static Result notFoundJson(ApiResponse<?> response) {
		try {
			return notFound(response.toPrettyJSON()).as(CONTENT_TYPE_JSON);
		} catch (Throwable t) {
			return notFound(response.toJSON());
		}
	}
	
	/**
	 * Transform an exception into code
	 * @param t exception
	 * @return code
	 */
	protected static int getCodeForException(Throwable t) {
		if(t instanceof NoSuchCityException) {
			return ApiResultCode.ERROR_PARAMETER_WRONG_VALUE.getCode();
		}
		return ApiResultCode.UNKNOWN.getCode();
	}
}
