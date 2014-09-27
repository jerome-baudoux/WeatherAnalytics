package services.weather;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import api.objects.City;
import api.objects.ConsolidatedDays;
import api.objects.Speed;
import api.objects.Temperature;
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
	public HistoryData getHistory(City city, String begin, String end) throws NoSuchCityException {
		if(!this.cities.contains(city)) {
			throw new NoSuchCityException(city);
		}

		// TODO -- Use real history data
		List<WeatherDay> days = getForecast(city);
		
		// Build consolidation using map/reduce
		ConsolidatedDays consolidation = days.stream()
			.map(WeatherServiceImpl::createHistory)
			.reduce(WeatherServiceImpl::mergeHistory)
			.orElse(new ConsolidatedDays());
		
		return new HistoryData(days, consolidation);
	}
	
	/**
	 * Build a consolidation for the current day
	 * @param day current day
	 * @return consolidation
	 */
	private static ConsolidatedDays createHistory(WeatherDay day) {
		return new ConsolidatedDays()
			// Wind
			.setMaxWindSpeed(day.getWindSpeed())
			.setMinWindSpeed(day.getWindSpeed())
			.setSumWindSpeed(day.getWindSpeed())
			.setNbWindSpeed(day.getWindSpeed()!=null ? 1 : 0)
			// Temperature
			.setMaxTemperature(day.getTemperatureMax())
			.setMinTemperature(day.getTemperatureMin())
			.setSumMaxTemperature(day.getTemperatureMax())
			.setNbMaxTemperature(day.getTemperatureMax()!=null ? 1 : 0)
			// Precipitation
			.setMaxPrecipitation(day.getPrecipitation())
			.setMinPrecipitation(day.getPrecipitation())
			.setSumPrecipitation(day.getPrecipitation())
			.setNbPrecipitation(day.getPrecipitation()!=null ? 1 : 0)
			// Conditions
			.setNbSunnyDays((day.getConditions()!= null && day.getConditions().isSunny()) ? 1 : 0)
			.setNbRainyDays((day.getConditions()!= null && day.getConditions().isRainy()) ? 1 : 0)
			.setNbSnowyDays((day.getConditions()!= null && day.getConditions().isSnowy()) ? 1 : 0);
	}
	
	/**
	 * Merge two ConsolidatedDays
	 * @param first first ConsolidatedDays
	 * @param second second ConsolidatedDays
	 * @return merged ConsolidatedDays
	 */
	private static ConsolidatedDays mergeHistory(ConsolidatedDays first, ConsolidatedDays second) {
		return new ConsolidatedDays()
		// Min
		.setMinWindSpeed(getMin(first.getMinWindSpeed(), second.getMinWindSpeed()))
		.setMinTemperature(getMin(first.getMinTemperature(), second.getMinTemperature()))
		.setMinPrecipitation(getMin(first.getMinPrecipitation(), second.getMinPrecipitation()))
		// Max
		.setMaxWindSpeed(getMax(first.getMaxWindSpeed(), second.getMaxWindSpeed()))
		.setMaxTemperature(getMax(first.getMaxTemperature(), second.getMaxTemperature()))
		.setMaxPrecipitation(getMax(first.getMaxPrecipitation(), second.getMaxPrecipitation()))
		// Sum
		.setSumWindSpeed(Speed.add(first.getSumWindSpeed(), second.getSumWindSpeed()))
		.setSumMaxTemperature(Temperature.add(first.getSumMaxTemperature(), second.getSumMaxTemperature()))
		.setSumPrecipitation(add(first.getSumPrecipitation(), second.getSumPrecipitation()))
		// Nb
		.setNbWindSpeed(first.getNbWindSpeed() + second.getNbWindSpeed())
		.setNbMaxTemperature(first.getNbMaxTemperature() + second.getNbMaxTemperature())
		.setNbPrecipitation(first.getNbPrecipitation() + second.getNbPrecipitation())
		// Conditions
		.setNbSunnyDays(first.getNbSunnyDays() + second.getNbSunnyDays())
		.setNbRainyDays(first.getNbRainyDays() + second.getNbRainyDays())
		.setNbSnowyDays(first.getNbSnowyDays() + second.getNbSnowyDays());
	}

	/**
	 * Get min value of a comparable
	 * @param first first
	 * @param second second
	 * @return min
	 */
	private static <T extends Comparable<T>> T getMin(T first, T second) {
		if(first == null) {
			return second;
		}
		if(first.compareTo(second) < 0 ) {
			return first;
		}
		return second;
	}
	
	/**
	 * Get max value of a comparable
	 * @param first first
	 * @param second second
	 * @return max
	 */
	private static <T extends Comparable<T>> T getMax(T first, T second) {
		if(first == null) {
			return second;
		}
		if(first.compareTo(second) > 0 ) {
			return first;
		}
		return second;
	}
	
	/**
	 * Adds up two double
	 * @param first first
	 * @param second second
	 * @return sum
	 */
	private static Double add(Double first, Double second) {
		if(first == null) {
			return second;
		}
		if(second == null) {
			return first;
		}
		return first + second;
	}
}
