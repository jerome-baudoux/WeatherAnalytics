package api.objects;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Weather conditions code
 * @author Jerome Baudoux
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WeatherDayConditions {
	
	UNKNOWN(0, "Unknown"),
	
	SUNNY(1, "Sunny"),
	CLOUDY(2, "Cloudy"),
	MIST(3, "Mist"),
	THUNDER(4, "Thunder"),
	DRIZZLE(5, "Drizzle"),

	LIGHT_RAIN(10, "Light rain"),
	LIGHT_SNOW(11, "Light snow"),
	LIGHT_ICE_PELLET(12, "Light ice pellet"),
	LIGHT_FROZEN_RAIN_OR_DRIZZLE(14, "Light frozen rain or drizzle"),
	
	LIGHT_RAIN_WITH_THUNDER(20, "Light rain with thunder"),
	LIGHT_SNOW_WITH_THUNDER(21, "Light snow with thunder"),

	MODERATE_RAIN(30, "Moderate rain"),
	MODERATE_SNOW(31, "Moderate snow"),
	MODERATE_ICE_PELLET(32, "Moderate ice pellet"),
	MODERATE_FROZEN_RAIN_OR_DRIZZLE(34, "Moderate frozen rain or drizzle"),
	
	MODERATE_OR_HEAVY_RAIN_WITH_THUNDER(40, "Moderate or heavy rain with thunder"),
	MODERATE_OR_HEAVY_SNOW_WITH_THUNDER(41, "Moderate or heavy snow with thunder"),

	HEAVY_RAIN(50, "Heavy rain"),
	HEAVY_SNOW(51, "Heavy snow"),
	HEAVY_ICE_PELLET(52, "Heavy ice pellet"),
	HEAVY_FROZEN_RAIN_OR_DRIZZLE(54, "Heavy frozen rain or drizzle");

	private final int code;
	private final String description;
	
	private WeatherDayConditions(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
