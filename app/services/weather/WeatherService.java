package services.weather;

import java.util.Date;
import java.util.List;

import api.objects.City;
import api.objects.WeatherDay;

/**
 * A global service for Weather related queries
 * @author Jerome Baudoux
 */
public interface WeatherService {

	/**
	 * @return a lit of cities available
	 */
	public List<City> getCities();

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
	
	/**
	 * Fetch all known forecast for this city
	 * @param city city to look for
	 * @param begin from this date
	 * @param end until this date
	 * @return all known forecast
	 */
	public List<WeatherDay> getHistory(String city, Date begin, Date end);
}
