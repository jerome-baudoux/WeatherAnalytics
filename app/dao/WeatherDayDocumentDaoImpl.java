package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import play.Logger;
import play.api.Play;
import play.api.db.DB;
import play.libs.Json;
import api.objects.WeatherDay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;

/**
 * A DAO for fetching weather days
 * @author Jerome Baudoux
 */
@Singleton
public class WeatherDayDocumentDaoImpl implements WeatherDayDocumentDao {

	/**
	 * Saves the weather day
	 */
	@Override
	public void createOrUpdate(WeatherDay day) {
		
		Connection connection = null;
		try {

			// Transform object into JSON
			String json = new ObjectMapper().writer().writeValueAsString(day).replace("'", "\\'");
			
			// New connection
			connection = getConnection();

			// Check if the data already exists
			String selectQuery = "SELECT count(*) as nb" + 
									" FROM " + getTableName() + 
									" WHERE NAME='" + day.getCity().getNameAndCountry() + "'" + 
									"   and TYPE='" + getType() + "'" + 
									"   and DATE = to_date('" + day.getDate() + "', '" + WeatherDay.DATE_PATTERN + "')";
			Logger.trace("Select query: " + selectQuery);
			ResultSet rs = connection.createStatement().executeQuery(selectQuery);

			// If so Update
			if(rs.next() && rs.getInt("nb")>0) {
				
				String updateQuery = "UPDATE " + getTableName() +
						" SET CONTENT='" + json + "'" +
						" WHERE NAME='" + day.getCity().getNameAndCountry() + "'" + 
						"   and TYPE='" + getType() + "'" + 
						"   and DATE = to_date('" + day.getDate() + "', '" + WeatherDay.DATE_PATTERN + "')";
				Logger.trace("Update query: " + updateQuery);
				
				connection.createStatement().executeUpdate(updateQuery);
				
			// Otherwise Create
			} else {

				String insertQuery = "INSERT INTO " + getTableName() + "(NAME, DATE, TYPE, CONTENT)" +
						"VALUES ("
						+ "'" + day.getCity().getNameAndCountry() + "', "
						+ "to_date('" + day.getDate() + "', "
						+ "'" + WeatherDay.DATE_PATTERN + "') , "
						+ "'" + getType() + "', '" + json + "')";
				Logger.trace("Insert query: " + insertQuery);
				
				connection.createStatement().executeUpdate(insertQuery);
			}
			
		} catch (Throwable t) {
			Logger.error("Error while saving day for city: " + day.getCity().getNameAndCountry(), t);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					Logger.error("Cannot close connection", e);
				}
			}
		}
	}

	
	/**
	 * Fetch WeatherDayDocument by date
	 * @param from starting date
	 * @param to ending date
	 * @return list of WeatherDayDocument matching
	 */
	@Override
	public List<WeatherDay> getByPeriode(String city, String from, String to) {
		
		List<WeatherDay> data = new LinkedList<>();
		
		Connection connection = null;
		try {
			// New connection
			connection = getConnection();
			
			// Fetch data
			String selectQuery = "SELECT CONTENT as json" + 
					" FROM " + getTableName() + 
					" WHERE NAME='" + city + "'" + 
					"   and TYPE='" + getType() + "'" + 
					"   and DATE >= to_date('" + from + "', '" + WeatherDay.DATE_PATTERN + "')" +
					"   and DATE <= to_date('" + to + "', '" + WeatherDay.DATE_PATTERN + "')";
			Logger.trace("Select query: " + selectQuery);
			ResultSet rs = connection.createStatement().executeQuery(selectQuery);
			
			// Transform into list
			while(rs.next()) {
				data.add(Json.fromJson(Json.parse(rs.getString("json")), WeatherDay.class));
			}
		} catch (Throwable t) {
			Logger.error("Error while fetching days for city: " + city, t);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					Logger.error("Cannot close connection", e);
				}
			}
		}
		
		return data;
	}
	
	/**
	 * @return new JDBC connection
	 */
	protected Connection getConnection() {
		return DB.getConnection("default", true, Play.current());
	}
	
	/**
	 * @return table name
	 */
	protected String getTableName() {
		return "TEMPORAL_DATA";
	}
	
	/**
	 * @return type of the document
	 */
	protected String getType() {
		return "WeatherDay";
	}
}
