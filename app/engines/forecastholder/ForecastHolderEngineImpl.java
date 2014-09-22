package engines.forecastholder;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	/**
	 * Internal cache
	 */
	protected Map<City, Set<WeatherDay>> cache;

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
			Set<WeatherDay> days = this.cache.get(forecast.getCity());
			if(days==null) {
				days = new LinkedHashSet<>();
				this.cache.put(forecast.getCity(), days);
			}
			days.add(forecast);
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
		Set<WeatherDay> days = this.cache.get(city);
		if(days==null) {
			return new LinkedList<WeatherDay>();
		}
		return new LinkedList<WeatherDay>(days);
	}
}
