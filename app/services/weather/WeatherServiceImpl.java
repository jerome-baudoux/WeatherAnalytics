package services.weather;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import api.objects.City;
import api.objects.WeatherDay;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import engines.forecastholder.ForecastHolderEngine;

/**
 * A global service for Weather related queries
 * @author Jerome Baudoux
 */
@Singleton
public class WeatherServiceImpl implements WeatherService {
	
	/**
	 * Forecast Holder Engine
	 */
	protected final ForecastHolderEngine forecastHolderEngine;
	
	/**
	 * Internal list of cities, sorted by alphabetical order
	 */
	protected final List<City> cities;
	
	/**
	 * Constructor
	 */
	@Inject
	public WeatherServiceImpl(ForecastHolderEngine forecastHolderEngine) {
		this.forecastHolderEngine = forecastHolderEngine;
		this.cities = buildCities();
	}

	/**
	 * @return a lit of cities available
	 */
	public List<City> getCities() {
		return this.cities;
	}

	/*
	 * Internal
	 */
	
	/**
	 * @return sorted list of the 10 most popular cities
	 */
	private List<City> buildCities() {
		List<City> cities = new LinkedList<>();
		cities.add(new City("London", "United Kingdom"));
		cities.add(new City("Bangkok", "Thailand"));
		cities.add(new City("Paris", "France"));
		cities.add(new City("Singapore", "Singapore"));
		cities.add(new City("Dubai", "United Arab Emirates"));
		cities.add(new City("New York", "United States of America"));
		cities.add(new City("Istanbul", "Turkey"));
		cities.add(new City("Kuala Lumpur", "Malaysia"));
		cities.add(new City("Hong Kong", "China"));
		cities.add(new City("Seoul", "South Korea"));
		Collections.sort(cities);
		return cities;
	}
	
	/**
	 * @param forecast data to be added
	 */
	@Override
	public void addForecast(List<WeatherDay> forecast) {
		this.forecastHolderEngine.addForecast(forecast);
	}

	/**
	 * Fetch all known forecast for this city
	 * @param city city to look for
	 * @return all known forecast
	 */
	@Override
	public List<WeatherDay> getForecast(City city) throws NoSuchCityException {
		if(!this.cities.contains(city)) {
			throw new NoSuchCityException(city);
		}
		return this.forecastHolderEngine.getForecast(city);
	}
	
	/**
	 * Fetch all known forecast for this city
	 * @param city city to look for
	 * @param begin from this date
	 * @param end until this date
	 * @return all known forecast
	 */
	@Override
	public List<WeatherDay> getHistory(City city, Date begin, Date end) throws NoSuchCityException {
		if(!this.cities.contains(city)) {
			throw new NoSuchCityException(city);
		}
		return new LinkedList<>();
	}
}
