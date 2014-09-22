package engines.forecastholder;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;

import play.Logger;
import api.objects.WeatherDay;

/**
 * A cache that holds few days worth of forecast
 * @author Jerome Baudoux
 */
public class ForecastHolderEngineImpl implements ForecastHolderEngine {

	/**
	 * on Startup
	 */
	@Override
	public void start() {
		// TODO
	}

	/**
	 * on Shutdown
	 */
	@Override
	public void stop() {
		// TODO
	}

	/**
	 * @param forecast data to be added
	 */
	@Override
	public void addForecast(List<WeatherDay> forecast) {
		
		// Just to be sure
		if(forecast==null) {
			return;
		}

		Logger.trace(forecast.size() + " days of forecast were stored in cache");
		
		// TODO
	}

	/**
	 * Fetch all known forecast for this city
	 * @param city city to look for
	 * @return all known forecast
	 */
	@Override
	@Nonnull
	public List<WeatherDay> getForecast(String city) {
		return new LinkedList<WeatherDay>();
	}
}
