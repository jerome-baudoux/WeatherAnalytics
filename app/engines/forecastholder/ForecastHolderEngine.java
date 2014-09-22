package engines.forecastholder;

import java.util.List;

import api.objects.City;
import api.objects.WeatherDay;
import engines.Engine;

/**
 * An engine that is used to hold the forecasts for a few days inside a cache.
 * @author Jerome Baudoux
 */
public interface ForecastHolderEngine extends Engine {

	/**
	 * @param forecast data to be added
	 */
	public void addForecast(List<WeatherDay> forecast);
	
	/**
	 * Fetch all known forecast for this city
	 * @param city city to look for
	 * @return all known forecast
	 */
	public List<WeatherDay> getForecast(City city);
}
