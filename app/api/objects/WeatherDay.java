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
	protected City city;
	
	/**
	 * Date, format: yyyy-MM-dd
	 */
	protected String date;
	
	/**
	 * Precipitation in millimeters
	 */
	protected double precipitation;
	
	/**
	 * Max temperature in celsius
	 */
	protected double temperatureMax;
	
	/**
	 * Min temperature in celsius
	 */
	protected double temperatureMin;
	
	/**
	 * Wind speed in Km/h
	 */
	protected double windSpeed;
	
	/**
	 * Wind direction in degree
	 */
	protected double windDirection;
	
	/**
	 * Code of the weather condition
	 */
	protected WeatherDayConditions conditions;

	/**
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public WeatherDay setCity(City city) {
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
	public double getPrecipitation() {
		return precipitation;
	}

	/**
	 * @param precipitation the precipitation to set
	 */
	public WeatherDay setPrecipitation(double precipitation) {
		this.precipitation = precipitation;
		return this;
	}

	/**
	 * @return the temperature max
	 */
	public double getTemperatureMax() {
		return temperatureMax;
	}

	/**
	 * @param temperatureMax the temperature max to set
	 */
	public WeatherDay setTemperatureMax(double temperatureMax) {
		this.temperatureMax = temperatureMax;
		return this;
	}

	/**
	 * @return the temperature min
	 */
	public double getTemperatureMin() {
		return temperatureMin;
	}

	/**
	 * @param temperatureMin the temperature min to set
	 */
	public WeatherDay setTemperatureMin(double temperatureMin) {
		this.temperatureMin = temperatureMin;
		return this;
	}

	/**
	 * @return the wind speed
	 */
	public double getWindSpeed() {
		return windSpeed;
	}

	/**
	 * @param windSpeed the wind speed to set
	 */
	public WeatherDay setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
		return this;
	}

	/**
	 * @return the wind direction
	 */
	public double getWindDirection() {
		return windDirection;
	}

	/**
	 * @param windDirection the wind direction to set
	 */
	public WeatherDay setWindDirection(double windDirection) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeatherDay other = (WeatherDay) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}
}
