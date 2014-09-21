package api.objects;

/**
 * A day worth of weather information
 * @author Jerome Baudoux
 *
 */
public class WeatherDay {

	protected String city;
	
	/**
	 * Date, format: yyyy-MM-dd
	 */
	protected String date;
	
	/**
	 * Precipitation in millimeters
	 */
	protected float precipitation;
	
	/**
	 * Max temperature in celsius
	 */
	protected float temperatureMax;
	
	/**
	 * Min temperature in celsius
	 */
	protected float temperatureMin;
	
	/**
	 * Wind speed in Km/h
	 */
	protected float windSpeed;
	
	/**
	 * Wind direction in degree
	 */
	protected float windDirection;
	
	/**
	 * Code of the weather condition
	 */
	protected int weatherCode;
	
	/**
	 * Description of the weather condition
	 */
	protected String weatherDescription;
}
