package dao;

import java.util.List;

import api.objects.WeatherDay;

/**
 * A DAO for fetching weather days
 * @author Jerome Baudoux
 */
public interface WeatherDayDocumentDao {
	
	void createOrUpdate(WeatherDay day);

	List<WeatherDay> getByPeriode(String city, String from, String to);
}
