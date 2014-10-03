package dao.temporal;

import java.text.SimpleDateFormat;

import api.objects.WeatherDay;

/**
 * A class that stores Weather condition for a city and a day
 * @author Jerome Baudoux
 */
public class WeatherDayDocument extends AbstractTemporalDocument<WeatherDay> { 

	/**
	 * Empty constructor
	 */
	public WeatherDayDocument() {}
	
	/**
	 * @param day day to build
	 */
	public WeatherDayDocument(WeatherDay day) throws Exception {
		super();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		// Fill content
		this.setName(day.getCity().getNameAndCountry());
		this.setDate(sdf.parse(day.getDate()));
		this.setDocument(day);
	}
}
