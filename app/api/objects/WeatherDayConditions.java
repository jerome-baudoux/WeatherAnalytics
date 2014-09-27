package api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Weather conditions code
 * An enum would have been prettier, but it caused issues with Jackson
 * @author Jerome Baudoux
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class WeatherDayConditions {
	
	public static final WeatherDayConditions UNKNOWN = new WeatherDayConditions(0, "Unknown");

	public static final WeatherDayConditions SUNNY = new WeatherDayConditions(1, "Sunny");
	public static final WeatherDayConditions CLOUDY = new WeatherDayConditions(2, "Cloudy");
	public static final WeatherDayConditions MIST = new WeatherDayConditions(3, "Mist");
	public static final WeatherDayConditions THUNDER = new WeatherDayConditions(4, "Thunder");
	public static final WeatherDayConditions DRIZZLE = new WeatherDayConditions(5, "Drizzle");

	public static final WeatherDayConditions LIGHT_RAIN = new WeatherDayConditions(10, "Light rain");
	public static final WeatherDayConditions LIGHT_SNOW = new WeatherDayConditions(11, "Light snow");
	public static final WeatherDayConditions LIGHT_SLEET = new WeatherDayConditions(12, "Light sleet");
	public static final WeatherDayConditions LIGHT_ICE_PELLET = new WeatherDayConditions(13, "Light ice pellet");
	public static final WeatherDayConditions LIGHT_FROZEN_RAIN_OR_DRIZZLE = new WeatherDayConditions(14, "Light frozen rain or drizzle");
	
	public static final WeatherDayConditions LIGHT_RAIN_WITH_THUNDER = new WeatherDayConditions(20, "Light rain with thunder");
	public static final WeatherDayConditions LIGHT_SNOW_WITH_THUNDER = new WeatherDayConditions(21, "Light snow with thunder");

	public static final WeatherDayConditions MODERATE_RAIN = new WeatherDayConditions(30, "Moderate rain");
	public static final WeatherDayConditions MODERATE_SNOW = new WeatherDayConditions(31, "Moderate snow");
	public static final WeatherDayConditions MODERATE_SLEET = new WeatherDayConditions(32, "Moderate sleet");
	public static final WeatherDayConditions MODERATE_ICE_PELLET = new WeatherDayConditions(33, "Moderate ice pellet");
	public static final WeatherDayConditions MODERATE_FROZEN_RAIN_OR_DRIZZLE = new WeatherDayConditions(34, "Moderate frozen rain or drizzle");
	
	public static final WeatherDayConditions MODERATE_OR_HEAVY_RAIN_WITH_THUNDER = new WeatherDayConditions(40, "Moderate or heavy rain with thunder");
	public static final WeatherDayConditions MODERATE_OR_HEAVY_SNOW_WITH_THUNDER = new WeatherDayConditions(41, "Moderate or heavy snow with thunder");

	public static final WeatherDayConditions HEAVY_RAIN = new WeatherDayConditions(50, "Heavy rain");
	public static final WeatherDayConditions HEAVY_SNOW = new WeatherDayConditions(51, "Heavy snow");
	public static final WeatherDayConditions HEAVY_SLEET = new WeatherDayConditions(52, "Heavy sleet");
	public static final WeatherDayConditions HEAVY_ICE_PELLET = new WeatherDayConditions(53, "Heavy ice pellet");
	public static final WeatherDayConditions HEAVY_FROZEN_RAIN_OR_DRIZZLE = new WeatherDayConditions(54, "Heavy frozen rain or drizzle");

	private final int code;
	private final String description;
	
	@JsonCreator
	private WeatherDayConditions(
			@JsonProperty("code") int code,
			@JsonProperty("description") String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	@JsonIgnore
	public boolean isSunny() {
		return this.code == SUNNY.getCode();
	}

	@JsonIgnore
	public boolean isRainy() {
		return this.code >= 10 && this.code % 10 == 0;
	}

	@JsonIgnore
	public boolean isSnowy() {
		return this.code >= 10 && this.code % 10 == 1;
	}

	@Override
	public String toString() {
		return this.description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.code;
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
		WeatherDayConditions other = (WeatherDayConditions) obj;
		if (this.code != other.code)
			return false;
		return true;
	}
}
