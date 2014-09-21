package api.objects;

/**
 * A day worth of weather information
 * @author Jerome Baudoux
 *
 */
public class WeatherDay {

	/**
	 * Current city
	 */
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
	protected WeatherDayConditions conditions;

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public WeatherDay setCity(String city) {
		this.city = city;
		return this;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public WeatherDay setDate(String date) {
		this.date = date;
		return this;
	}

	/**
	 * @return the precipitation
	 */
	public float getPrecipitation() {
		return precipitation;
	}

	/**
	 * @param precipitation the precipitation to set
	 */
	public WeatherDay setPrecipitation(float precipitation) {
		this.precipitation = precipitation;
		return this;
	}

	/**
	 * @return the temperature max
	 */
	public float getTemperatureMax() {
		return temperatureMax;
	}

	/**
	 * @param temperatureMax the temperature max to set
	 */
	public WeatherDay setTemperatureMax(float temperatureMax) {
		this.temperatureMax = temperatureMax;
		return this;
	}

	/**
	 * @return the temperature min
	 */
	public float getTemperatureMin() {
		return temperatureMin;
	}

	/**
	 * @param temperatureMin the temperature min to set
	 */
	public WeatherDay setTemperatureMin(float temperatureMin) {
		this.temperatureMin = temperatureMin;
		return this;
	}

	/**
	 * @return the wind speed
	 */
	public float getWindSpeed() {
		return windSpeed;
	}

	/**
	 * @param windSpeed the wind speed to set
	 */
	public WeatherDay setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
		return this;
	}

	/**
	 * @return the wind direction
	 */
	public float getWindDirection() {
		return windDirection;
	}

	/**
	 * @param windDirection the wind direction to set
	 */
	public WeatherDay setWindDirection(float windDirection) {
		this.windDirection = windDirection;
		return this;
	}

	/**
	 * @return the conditions
	 */
	public WeatherDayConditions getConditions() {
		return conditions;
	}

	/**
	 * @param conditions the conditions to set
	 */
	public WeatherDay setConditions(WeatherDayConditions conditions) {
		this.conditions = conditions;
		return this;
	}
}
