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
	 * @return Pong
	 */
	public Result ping() {
		return ok(
			new SimpleApiResponse()
				.setResult(ApiResultCode.SUCCESS)
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
				.setResult(ApiResultCode.ERROR_API_NOT_FOUND)
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
					.setResult(ApiResultCode.SUCCESS)
					.setCities(this.weatherService.getCities())
					.toJSON()
			);
		} catch (Throwable t) {
			return getError(t);
		}
	}
	
	/**
	 * Gets the forecast for the specified city
	 * @return
	 */
	public Promise<Result> forecast(String city, String country) {
		return Promise.promise(() -> {
			return this.weatherService.getForecast(new City(city, country));
		}).map((List<WeatherDay> days) -> {
			if(days.size()==0) {
				return ok(
					new ForecastResponse()
						.setResult(ApiResultCode.ERROR_PARAMETER_WRONG_VALUE)
						.setMessage("Unknown city: " + city + ", " + country)
						.toJSON()
				);
			} else {
				return ok(
					new ForecastResponse()
						.setResult(ApiResultCode.SUCCESS)
						.setForecast(days)
						.toJSON()
				);
			}
		});
	}
	
	/**
	 * For debug only
	 * @return sends a message
	 */
	public Promise<Result> longOperation(int time) {
		return Promise.promise(() -> {
			Thread.sleep(time);
			return "Current time: " + System.currentTimeMillis();
		}).map((String message) -> {
			return ok(new SimpleApiResponse().setResult(ApiResultCode.SUCCESS)
					.setMessage(message).toJSON());
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
				.setResult(ApiResultCode.ERROR_UNKNOWN)
				.setMessage(t.getMessage())
				.setError(t)
				.toJSON()
		);
	}
}
