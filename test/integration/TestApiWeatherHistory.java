package integration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.*;

import api.objects.City;
import api.objects.ConsolidatedDays;
import api.objects.Speed;
import api.objects.Temperature;
import api.objects.WeatherDay;
import api.objects.WeatherDayConditions;
import api.response.ApiResultCode;
import api.response.weather.HistoryResponse;
import play.api.Play;
import play.api.db.DB;
import play.test.TestBrowser;
import services.weather.WeatherService;
import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.fail;

/**
 * Test the Weather APIs
 * This class also tests the DAO :)
 * @author Jerome Baudoux
 */
public class TestApiWeatherHistory extends AbstractIntegrationTest {
    
    protected static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * Fetch history for cities that does not exist
	 * Expected result -> error code
	 */
    @Test
    public void testHistoryWrongCity() {
    	runTestBrowser("api/history/not/existing/from/2014-01-01/until/2014-01-01", (TestBrowser browser) -> {
    		HistoryResponse response = getJson(browser, HistoryResponse.class);
            assertThat(response.getResult()).isEqualTo(ApiResultCode.ERROR_PARAMETER_WRONG_VALUE.getCode());
    	});
    }
    
	/**
	 * Fetch history with an until date after the from date
	 * Expected result -> error code
	 */
    @Test
    public void testHistoryWrongRange() {
    	runTestBrowser("api/history/Paris/France/from/2014-01-02/until/2014-01-01", (TestBrowser browser) -> {
    		HistoryResponse response = getJson(browser, HistoryResponse.class);
            assertThat(response.getResult()).isEqualTo(ApiResultCode.ERROR_PARAMETER_WRONG_VALUE.getCode());
    	});
    }
    
	/**
	 * Fetch forecast for cities that does exist and has forecast
	 * Expected result -> 5 empty forecast
	 */
    @Test
    public void testHistoryOK() {
    	
    	final City city = new City("Paris", "France");
    	final List<WeatherDay> forecast = new LinkedList<WeatherDay>();
    	
    	final LocalDateTime startDate = LocalDateTime.now().minusDays(1);
    	
    	// Builds a list of 6 days.
    	// One in the past, 5 in the future
    	for(int i=0; i<6; i++) {
    		forecast.add(new WeatherDay()
    			.setCity(city)
    			.setDate(startDate.plusDays(i).format(dtf))
    			.setPrecipitation(new Double(i))
    			.setTemperatureMax(new Temperature().setCelsius(i).setFahrenheit(i*2))
    			.setTemperatureMin(new Temperature().setCelsius(i*3).setFahrenheit(i*4))
    			.setWindSpeed(new Speed().setKmph(i*5).setMph(i*6))
    			.setWindDirection(new Integer(i*7))
    			.setConditions(WeatherDayConditions.SUNNY));
    	}

    	
    	// End to end test
    	runTestBrowser("api/history/Paris/France/from/"+startDate.format(dtf)+"/until/"+startDate.plusDays(5).format(dtf), () -> {
    		
    		try {
				createTable();
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
    		
        	// Fills service
        	getInjector().getInstance(WeatherService.class).addForecast(forecast);
        	
    	}, (TestBrowser browser) -> {
    		
    		HistoryResponse response = getJson(browser, HistoryResponse.class);
            assertThat(response.getResult()).isEqualTo(ApiResultCode.SUCCESS.getCode());
            assertThat(response.getHistory()).hasSize(6);
            
            // Test history result
            for(int i=0; i<5; i++) {
            	
            	// Forecast day
            	WeatherDay day = response.getHistory().get(i);
            	
            	// City should be OK
            	assertThat(day.getCity()).isEqualTo(city);
            	
            	// Date should be OK
            	assertThat(day.getDate()).isEqualTo(LocalDateTime.now().plusDays(i-1).format(dtf));
            	
            	// Conditions should be unknown
            	assertThat(day.getConditions()).isEqualTo(WeatherDayConditions.SUNNY);
            	
            	// Other metrics should be empty
            	assertThat(day.getPrecipitation()).isEqualTo(new Double(i));
            	assertThat(day.getTemperatureMax().getCelsius()).isEqualTo(new Integer(i));
            	assertThat(day.getTemperatureMax().getFahrenheit()).isEqualTo(new Integer(i*2));
            	assertThat(day.getTemperatureMin().getCelsius()).isEqualTo(new Integer(i*3));
            	assertThat(day.getTemperatureMin().getFahrenheit()).isEqualTo(new Integer(i*4));
            	assertThat(day.getWindSpeed().getKmph()).isEqualTo(new Integer(i*5));
            	assertThat(day.getWindSpeed().getMph()).isEqualTo(new Integer(i*6));
            	assertThat(day.getWindDirection()).isEqualTo(new Integer(i*7));
            }
            
            // Test consolidation
            ConsolidatedDays conso = response.getConsolidation();

        	assertThat(conso.getMaxPrecipitation()).isEqualTo(5);
        	assertThat(conso.getMinPrecipitation()).isEqualTo(0);
        	assertThat(conso.getSumPrecipitation()).isEqualTo(15);
        	assertThat(conso.getNbPrecipitation()).isEqualTo(6);
        	
        	assertThat(conso.getMaxTemperature().getCelsius()).isEqualTo(5);
        	assertThat(conso.getMaxTemperature().getFahrenheit()).isEqualTo(10);
        	assertThat(conso.getMinTemperature().getCelsius()).isEqualTo(0);
        	assertThat(conso.getMinTemperature().getFahrenheit()).isEqualTo(0);
        	assertThat(conso.getSumMaxTemperature().getCelsius()).isEqualTo(15);
        	assertThat(conso.getSumMaxTemperature().getFahrenheit()).isEqualTo(30);
        	assertThat(conso.getNbMaxTemperature()).isEqualTo(6);

        	assertThat(conso.getMaxWindSpeed().getKmph()).isEqualTo(25);
        	assertThat(conso.getMaxWindSpeed().getMph()).isEqualTo(30);
        	assertThat(conso.getMinWindSpeed().getKmph()).isEqualTo(0);
        	assertThat(conso.getMinWindSpeed().getMph()).isEqualTo(0);
        	assertThat(conso.getSumWindSpeed().getKmph()).isEqualTo(75);
        	assertThat(conso.getSumWindSpeed().getMph()).isEqualTo(90);
        	assertThat(conso.getNbWindSpeed()).isEqualTo(6);

        	assertThat(conso.getNbSunnyDays()).isEqualTo(6);
        	assertThat(conso.getNbRainyDays()).isEqualTo(0);
        	assertThat(conso.getNbSnowyDays()).isEqualTo(0);
    	});
    }
    
    /**
     * Create tables inside database
     * @throws SQLException sql error
     * @throws IOException file error
     */
    protected void createTable() throws SQLException, IOException {

    	// Open connection and read file
    	try (
    			Connection connection = DB.getConnection("default", true, Play.current());
    			Stream<String> lines = Files.lines(Paths.get("data", "create_tables.sql"));
    	) {
    		// Create tables
    		connection.createStatement().execute(lines.collect(Collectors.joining(" ")));
    	}
    }
}
