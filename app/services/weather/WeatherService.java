package services.weather;

import java.io.Serializable;
import java.util.List;

import api.objects.City;
import api.objects.ConsolidatedDays;
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
	public List<WeatherDay> getForecast(City city) throws NoSuchCityException;
	
	/**
	 * Fetch all known forecast for this city
	 * @param city city to look for
	 * @param begin from this date
	 * @param end until this date
	 * @return all known forecast
	 */
	public HistoryData getHistory(City city, String begin, String end) throws NoSuchCityException;
	
	/**
	 * Result of a history request
	 * @author Jerome Baudoux
	 */
	public static class HistoryData implements Serializable {
		private static final long serialVersionUID = 1L;
		protected final List<WeatherDay> history;
		protected final ConsolidatedDays consolidation;
		
		/**
		 * Constructor
		 * @param history history
		 * @param consolidation consolidation
		 */
		public HistoryData(List<WeatherDay> history, ConsolidatedDays consolidation) {
			this.history = history;
			this.consolidation = consolidation;
		}

		/**
		 * @return the history
		 */
		public List<WeatherDay> getHistory() {
			return history;
		}

		/**
		 * @return the consolidation
		 */
		public ConsolidatedDays getConsolidation() {
			return consolidation;
		}
	}
	
	/**
	 * 
	 * @author Jerome Baudoux
	 *
	 */
	public static class NoSuchCityException extends Exception {

		private static final long serialVersionUID = 1L;
		
		protected City city;
		
		public NoSuchCityException(City city) {
			super("The city: " + city.getNameAndCountry() + " cannot be found");
		}
	}
}
