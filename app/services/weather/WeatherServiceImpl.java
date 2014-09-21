package services.weather;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Singleton;

/**
 * A global service for Weather related queries
 * @author Jerome Baudoux
 */
@Singleton
public class WeatherServiceImpl implements WeatherService {
	
	/**
	 * Internal list of cities, sorted by alphabetical order
	 */
	protected final List<String> cities;
	
	/**
	 * Constructor
	 */
	public WeatherServiceImpl() {
		this.cities = buildCities();
	}

	/**
	 * @return a lit of cities available
	 */
	public List<String> getCities() {
		return this.cities;
	}

	/*
	 * Internal
	 */
	
	/**
	 * @return sorted list of the 10 most popular cities
	 */
	private List<String> buildCities() {
		List<String> cities = new LinkedList<>();
		cities.add("London, England");
		cities.add("Bangkok, Thailand");
		cities.add("Paris, France");
		cities.add("Singapore, Singapore");
		cities.add("Dubai, United Arab Emirates");
		cities.add("New York, United States");
		cities.add("Istanbul, Turkey");
		cities.add("Kuala Lumpur, Malaysia");
		cities.add("Hong Kong, China");
		cities.add("Seoul, South Korea");
		Collections.sort(cities);
		return cities;
	}
}
