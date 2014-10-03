package dao.temporal;

import java.util.List;

import com.google.inject.Singleton;

import dao.AbstractDaoImpl;

/**
 * A DAO for fetching weather days
 * @author Jerome Baudoux
 */
@Singleton
public class WeatherDayDocumentDaoImpl extends AbstractDaoImpl implements WeatherDayDocumentDao {

	/**
	 * Fetch WeatherDayDocument by date
	 * @param from starting date
	 * @param to ending date
	 * @return list of WeatherDayDocument matching
	 */
	public List<WeatherDayDocument> getByPeriode(String city, String from, String to) {
		return null;
	}
}
