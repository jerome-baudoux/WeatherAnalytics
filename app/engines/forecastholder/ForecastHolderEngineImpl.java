package engines.forecastholder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.google.inject.Singleton;

import play.Logger;
import api.objects.City;
import api.objects.WeatherDay;

/**
 * A cache that holds few days worth of forecast
 * @author Jerome Baudoux
 */
@Singleton
public class ForecastHolderEngineImpl implements ForecastHolderEngine {
	
	// TODO -- Purge the cache
	
	protected static final int FORECAST_DAYS = 5;
	
	/**
	 * Internal cache
	 */
	protected Map<City, Map<String, WeatherDay>> cache;
	
	/**
	 * Date formatter
	 */
	protected DateTimeFormatter formatter;
	
	/**
	 * Constructor
	 */
	public ForecastHolderEngineImpl() {
		this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	}

	/**
	 * on Startup
	 */
	@Override
	public void start() {
		this.cache = new HashMap<>();
	}

	/**
	 * on Shutdown
	 */
	@Override
	public void stop() {
		this.cache.clear();
	}

	/**
	 * @param forecast data to be added
	 */
	@Override
	public void addForecast(List<WeatherDay> forecast) {
		
		// Just to be sure
		if(forecast==null || forecast.size()==0) {
			return;
		}
		
		// Do it item by item
		for(WeatherDay day: forecast) {
			addForecast(day);
		}

		Logger.trace(forecast.size() + " days of forecast were stored in cache");
	}
	
	/**
	 * @param forecast forecast data to be added
	 */
	private void addForecast(WeatherDay forecast) {
		synchronized (this.cache) {
			Map<String, WeatherDay> days = this.cache.get(forecast.getCity());
			if(days==null) {
				days = new HashMap<>();
				this.cache.put(forecast.getCity(), days);
			}
			days.put(forecast.getDate(), forecast);
		}
	}

	/**
	 * Fetch all known forecast for this city
	 * @param city city to look for
	 * @return all known forecast
	 */
	@Override
	@Nonnull
	public List<WeatherDay> getForecast(City city) {
		List<WeatherDay> daysToReturn = new LinkedList<WeatherDay>();
		
		// For the next 5 days
		LocalDateTime now = LocalDateTime.now();
		for(int i=0; i<FORECAST_DAYS; i++) {
			daysToReturn.add(getForecast(city, now.format(formatter)));
			now.plusDays(1);
		}
		return daysToReturn;
	}
	
	/**
	 * Fetch a day of data from the cache
	 * @param city city
	 * @param date day
	 * @return data
	 */
	protected WeatherDay getForecast(City city, String date) {
		synchronized (this.cache) {
			// Check if the city is known
			Map<String, WeatherDay> days = this.cache.get(city);
			if(days!=null) {
				// Check if the day is known
				WeatherDay day = days.get(date);
				if(days!=null) {
					return day;
				}
			}
			return new WeatherDay().setCity(city).setDate(date);
		}
	}
}
