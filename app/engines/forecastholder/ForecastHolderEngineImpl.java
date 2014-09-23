package engines.forecastholder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
	
	// TODO -- Handle timezones ?
	
	protected static final long DELAY = 1000 * 60 * 60 * 12; // 12 hours

	protected static final int FORECAST_DAYS = 5;
	protected static final int FORECAST_DAYS_RETENTIONS = 1;
	
	/**
	 * Internal cache
	 */
	protected Map<City, Map<String, WeatherDay>> cache;
	
	/**
	 * Date formatter
	 */
	protected DateTimeFormatter formatter;

	protected TimerTask timerTask;
	protected Timer timer;
	
	/**
	 * Constructor
	 */
	public ForecastHolderEngineImpl() {
		this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.cache = new HashMap<>();
	}

	/**
	 * on Startup
	 */
	@Override
	public void start() {
		this.cache.clear();
		this.timerTask = new CleanTask();
		this.timer = new Timer(true);
		this.timer.scheduleAtFixedRate(timerTask, DELAY, DELAY);
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
			now = now.plusDays(1);
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
	
	/*
	 * Internal
	 */
	
	/**
	 * The cleaning task
	 * @author Jerome Baudoux
	 */
	protected class CleanTask extends TimerTask {
		@Override
		public void run() {
			LocalDateTime threshold = LocalDateTime.now().minusDays(FORECAST_DAYS_RETENTIONS);
			synchronized (ForecastHolderEngineImpl.this.cache) {
				// For every city
				for(Map<String, WeatherDay> days : ForecastHolderEngineImpl.this.cache.values()) {
					// For every days
					for(String date: days.keySet()) {
						// If day if older than threshold
						if(LocalDateTime.parse(date, formatter).isBefore(threshold)) {
							days.remove(date);
						}
					}
				}
			}
		}
	}
}
