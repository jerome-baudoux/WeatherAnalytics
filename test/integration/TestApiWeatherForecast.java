package integration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.junit.*;

import api.objects.City;
import api.objects.Speed;
import api.objects.Temperature;
import api.objects.WeatherDay;
import api.objects.WeatherDayConditions;
import api.response.ApiResultCode;
import api.response.weather.ForecastResponse;
import play.test.TestBrowser;
import services.weather.WeatherService;
import static org.fest.assertions.Assertions.*;

/**
 * Test the Weather APIs
 * @author Jerome Baudoux
 */
public class TestApiWeatherForecast extends AbstractIntegrationTest {
    
    protected static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * Fetch forecast for cities that does not exist
	 * Expected result -> 5 empty forecast
	 */
    @Test
    public void testForecastNoData() {
    	runTestBrowser("api/forecast/not/existing", (TestBrowser browser) -> {
    		ForecastResponse response = getJson(browser, ForecastResponse.class);
            assertThat(response.getResult()).isEqualTo(ApiResultCode.ERROR_PARAMETER_WRONG_VALUE.getCode());
    	});
    }
    
	/**
	 * Fetch forecast for cities that does exist and has forecast
	 * Expected result -> 5 empty forecast
	 */
    @Test
    public void testForecastOK() {
    	
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
    	runTestBrowser("api/forecast/Paris/France", () -> {
    		
        	// Fills service
        	getInjector().getInstance(WeatherService.class).addForecast(forecast);
        	
    	}, (TestBrowser browser) -> {
    		
    		ForecastResponse response = getJson(browser, ForecastResponse.class);
            assertThat(response.getResult()).isEqualTo(ApiResultCode.SUCCESS.getCode());
            assertThat(response.getForecast()).hasSize(5);
            
            // Test forecast result
            for(int i=1; i<5; i++) {
            	
            	// The first value here is the second value from the list sent
            	// Since one value was in the past
            	int j = i+1;
            	
            	// Forecast day
            	WeatherDay day = response.getForecast().get(i);
            	
            	// City should be OK
            	assertThat(day.getCity()).isEqualTo(city);
            	
            	// Date should be OK
            	assertThat(day.getDate()).isEqualTo(LocalDateTime.now().plusDays(i).format(dtf));
            	
            	// Conditions should be unknown
            	assertThat(day.getConditions()).isEqualTo(WeatherDayConditions.SUNNY);
            	
            	// Other metrics should be empty
            	assertThat(day.getPrecipitation()).isEqualTo(new Double(j));
            	assertThat(day.getTemperatureMax().getCelsius()).isEqualTo(new Integer(j));
            	assertThat(day.getTemperatureMax().getFahrenheit()).isEqualTo(new Integer(j*2));
            	assertThat(day.getTemperatureMin().getCelsius()).isEqualTo(new Integer(j*3));
            	assertThat(day.getTemperatureMin().getFahrenheit()).isEqualTo(new Integer(j*4));
            	assertThat(day.getWindSpeed().getKmph()).isEqualTo(new Integer(j*5));
            	assertThat(day.getWindSpeed().getMph()).isEqualTo(new Integer(j*6));
            	assertThat(day.getWindDirection()).isEqualTo(new Integer(j*7));
            }
    	});
    }
}
