package controllers;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import api.objects.City;
import api.objects.WeatherDay;
import api.response.ApiResultCode;
import api.response.SimpleApiResponse;
import api.response.weather.CitiesResponse;
import api.response.weather.ForecastResponse;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;
import services.weather.WeatherService;

/**
 * A controller for all public API
 * @author Jerome Baudoux
 */
@Singleton
public class Api extends Controller {
	
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
		return ok(new SimpleApiResponse()
			.setResult(ApiResultCode.SUCCESS.getCode())
			.toJSON()
		);
	}

	/**
	 * When an unknown API was called
	 * @param name name of the api
	 * @return 404 error
	 */
	public Result apiNotFound(String name) {
		return notFound(new SimpleApiResponse()
			.setResult(ApiResultCode.ERROR_API_NOT_FOUND.getCode())
			.setMessage("No API found for the name: " + name)
			.toJSON()
		);
	}
	
	/**
	 * Fetch the list of supported cities
	 * @return a list of cities
	 */
	public Promise<Result> cities() {
		
		// Request / Result
		return Promise.promise(() -> (Result) ok(new CitiesResponse()	
				.setResult(ApiResultCode.SUCCESS.getCode())
				.setCities(this.weatherService.getCities())
				.toJSON())
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
		).map((List<WeatherDay> days) -> (Result) ok(new ForecastResponse()
				.setResult(ApiResultCode.SUCCESS.getCode())
				.setForecast(days)
				.toJSON())
				
		// Error
		).recoverWith(Api::getError);
	}
	
	/*
	 * Internal
	 */
	
	/**
	 * For debug only
	 * @return sends a message
	 */
	public Promise<Result> longOperation(int time) {
		
		// Request
		return Promise.promise(() -> {
			Thread.sleep(time);
			return "Current time: " + System.currentTimeMillis();
			
		// Result
		}).map((String message) -> (Result) ok(new SimpleApiResponse()
				.setResult(ApiResultCode.SUCCESS.getCode())
				.setMessage(message).toJSON())
			
		// Error
		).recoverWith(Api::getError);
	}
	
	/**
	 * Return an error message as a promise
	 * @param t throwable
	 * @return error message
	 */
	protected static Promise<Result> getError(Throwable t) {
		return Promise.promise(() -> (Result) internalServerError(new SimpleApiResponse()
				.setResult(ApiResultCode.ERROR_UNKNOWN.getCode())
				.setMessage(t.getMessage())
				.setError(t)
				.toJSON())
		);
	}
}
