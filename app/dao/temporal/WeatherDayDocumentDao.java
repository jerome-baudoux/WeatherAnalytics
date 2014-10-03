package dao.temporal;

import java.util.List;

import dao.AbstractDao;

/**
 * A DAO for fetching weather days
 * @author Jerome Baudoux
 */
public interface WeatherDayDocumentDao extends AbstractDao {

	List<WeatherDayDocument> getByPeriode(String city, String from, String to);
}
