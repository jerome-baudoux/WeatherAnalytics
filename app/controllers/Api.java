package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import api.ApiResultConstants;
import api.SimpleApiResponse;
import api.weather.CitiesResponse;
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
	 * @return Pong
	 */
	public Result ping() {
		return ok(
			new SimpleApiResponse()
				.setResult(ApiResultConstants.SUCCESS)
				.toJSON()
		);
	}

	/**
	 * When an unknown API was called
	 * @param name name of the api
	 * @return 404 error
	 */
	public Result apiNotFound(String name) {
		return notFound(
			new SimpleApiResponse()
				.setResult(ApiResultConstants.ERROR_API_NOT_FOUND)
				.setMessage("No API found for the name: " + name)
				.toJSON()
		);
	}
	
	/**
	 * Fetch the list of supported cities
	 * @return a list of cities
	 */
	public Result cities() {
		try {
			return ok(
				new CitiesResponse()	
					.setResult(ApiResultConstants.SUCCESS)
					.setCities(this.weatherService.getCities())
					.toJSON()
			);
		} catch (Throwable t) {
			return getError(t);
		}
	}
	
	/**
	 * For debug only
	 * @return sends a message
	 */
	public Promise<Result> longOperation(int time) {

	    Promise<String> promise = Promise.promise(() -> {
	    	Thread.sleep(time);
	    	return "Current time: " + System.currentTimeMillis();
	    });     
	    
	    return promise.map((String message) -> {
	    	return ok(
				new SimpleApiResponse()
					.setResult(ApiResultConstants.SUCCESS)
					.setMessage(message)
					.toJSON()
			);
	    });
	}
	
	/*
	 * Internal
	 */
	
	/**
	 * Return an error message
	 * @param t throwable
	 * @return error message
	 */
	protected Result getError(Throwable t) {
		return internalServerError(
			new SimpleApiResponse()
				.setResult(ApiResultConstants.ERROR_UNKNOWN)
				.setMessage(t.getMessage())
				.setError(t)
				.toJSON()
		);
	}
}
