package api.response.weather;

import java.util.List;

import api.objects.WeatherDay;
import api.response.ApiResponse;

/**
 * Response of a forecast request
 * @author Jerome Baudoux
 */
public class ForecastResponse extends ApiResponse<ForecastResponse> {

	/**
	 * Forecast
	 */
	protected List<WeatherDay> forecast;

	/**
	 * @return the forecast
	 */
	public List<WeatherDay> getForecast() {
		return forecast;
	}

	/**
	 * @param forecast the forecast to set
	 */
	public ForecastResponse setForecast(List<WeatherDay> forecast) {
		this.forecast = forecast;
		return this;
	}
}
