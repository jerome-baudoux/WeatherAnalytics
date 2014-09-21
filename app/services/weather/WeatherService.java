package services.weather;

import java.util.List;

/**
 * A global service for Weather related queries
 * @author Jerome Baudoux
 */
public interface WeatherService {

	/**
	 * @return a lit of cities available
	 */
	public List<String> getCities();
}
