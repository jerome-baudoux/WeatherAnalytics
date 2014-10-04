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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;

/**
 * A DAO for fetching weather days
 * @author Jerome Baudoux
 */
@Singleton
public class WeatherDayDocumentDaoImpl implements WeatherDayDocumentDao {

	/**
	 * @param day day to save
	 * Saves the weather day
	 */
	@Override
	public void createOrUpdate(WeatherDay day) {
		
		Connection connection = null;
		try {
			// New connection
			connection = getConnection();

			// Check if the data already exists
			String selectQuery = "SELECT count(*) as nb" + 
									" FROM " + getTableName() + 
									" WHERE NAME='" + day.getCity().getNameAndCountry() + "'" + 
									"  and TYPE='" + getType() + "'" + 
									"  and DATE = to_date('" + day.getDate() + "', '" + WeatherDay.DATE_PATTERN + "')";
			Logger.trace("Select query: " + selectQuery);
			ResultSet rs = connection.createStatement().executeQuery(selectQuery);

			// If so Update
			if(rs.next() && rs.getInt("nb")>0) {
				update(day, connection);
			} else {
				// Otherwise Create				
				create(day, connection);
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
	
	/*
	 * Internal
	 */
	
	/**
	 * Create the day into the database
	 * @param day date to create
	 * @param connection opened connection (will not be closed)
	 * @throws JsonProcessingException potential parsing error
	 * @throws SQLException potential SQL error
	 */
	protected void create(WeatherDay day, Connection connection) throws JsonProcessingException, SQLException {
		
		// Transform object into JSON
		String json = getJson(day);
		
		// Prepare query
		String insertQuery = "INSERT INTO " + getTableName() + "(NAME, DATE, TYPE, CONTENT)" +
				"VALUES ("
				+ "'" + day.getCity().getNameAndCountry() + "', "
				+ "to_date('" + day.getDate() + "', "
				+ "'" + WeatherDay.DATE_PATTERN + "') , "
				+ "'" + getType() + "', '" + json + "')";
		Logger.trace("Insert query: " + insertQuery);
		
		// Create object
		connection.createStatement().executeUpdate(insertQuery);
	}
	
	/**
	 * Update the day into the database
	 * @param day date to update
	 * @param connection opened connection (will not be closed)
	 * @throws JsonProcessingException potential parsing error
	 * @throws SQLException potential SQL error
	 */
	protected void update(WeatherDay day, Connection connection) throws JsonProcessingException, SQLException {
		
		// Transform object into JSON
		String json = getJson(day);
		
		// Prepare query
		String updateQuery = "UPDATE " + getTableName() +
				" SET CONTENT='" + json + "'" +
				" WHERE NAME='" + day.getCity().getNameAndCountry() + "'" + 
				"   and TYPE='" + getType() + "'" + 
				"   and DATE = to_date('" + day.getDate() + "', '" + WeatherDay.DATE_PATTERN + "')";
		Logger.trace("Update query: " + updateQuery);
		
		// update object
		connection.createStatement().executeUpdate(updateQuery);
	}
	
	/**
	 * Converts a day into a JSON string
	 * @param day day
	 * @return JSON string
	 * @throws JsonProcessingException potential parsing error
	 */
	protected String getJson(WeatherDay day) throws JsonProcessingException {
		return new ObjectMapper().writer().writeValueAsString(day).replace("'", "\\'");
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
