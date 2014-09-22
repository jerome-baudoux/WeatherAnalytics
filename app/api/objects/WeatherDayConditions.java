package api.objects;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Weather conditions code
 * @author Jerome Baudoux
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WeatherDayConditions {
	
	UNKNOWN(0, "Unknown"),
	
	SUNNY(1, "Sunny"), // 113
	CLOUDY(2, "Cloudy"), // 116 122 119
	MIST(3, "Mist"), // 143 248
	THUNDER(4, "Thunder"), // 200
	DRIZZLE(5, "Drizzle"), // 263 266

	LIGHT_RAIN(10, "Light rain"), // 176 293 353 296
	LIGHT_SNOW(11, "Light snow"), // 179 323 368 326
	LIGHT_ICE_PELLET(12, "Light ice pellet"), // 182 362 317 374
	LIGHT_FROZEN_RAIN_OR_DRIZZLE(14, "Light frozen rain or drizzle"), // 185 311 260 
	
	LIGHT_RAIN_WITH_THUNDER(20, "Light rain with thunder"), // 386
	LIGHT_SNOW_WITH_THUNDER(21, "Light snow with thunder"), // 392

	MODERATE_RAIN(30, "Moderate rain"), // 302 299 356
	MODERATE_SNOW(31, "Moderate snow"), // 332 329
	MODERATE_ICE_PELLET(32, "Moderate ice pellet"), // 320 365 350
	MODERATE_FROZEN_RAIN_OR_DRIZZLE(34, "Moderate frozen rain or drizzle"), // 314 281
	
	MODERATE_OR_HEAVY_RAIN_WITH_THUNDER(40, "Moderate or heavy rain with thunder"), // 389
	MODERATE_OR_HEAVY_SNOW_WITH_THUNDER(41, "Moderate or heavy snow with thunder"), // 395

	HEAVY_RAIN(50, "Heavy rain"), // 308 359 305
	HEAVY_SNOW(51, "Heavy snow"), // 230 338 371 227 335
	HEAVY_ICE_PELLET(52, "Heavy ice pellet"), // 377
	HEAVY_FROZEN_RAIN_OR_DRIZZLE(54, "Heavy frozen rain or drizzle"); // 284

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
