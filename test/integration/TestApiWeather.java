package integration;

import org.junit.*;

import api.response.ApiResultCode;
import api.response.weather.CitiesResponse;
import play.test.TestBrowser;
import static org.fest.assertions.Assertions.*;

/**
 * Test the Weather APIs
 * @author Jerome Baudoux
 *
 */
public class TestApiWeather extends AbstractIntegrationTest {

	/**
	 * Fetch cities
	 * Expected result -> API OK 1
	 */
    @Test
    public void testCitiesOk() {

    	runTest("api/cities", (TestBrowser browser) -> {
    		CitiesResponse response = getJson(browser, CitiesResponse.class);
            assertThat(response.getResult()).isEqualTo(ApiResultCode.SUCCESS.getCode());
            assertThat(response.getCities()).hasSize(10);
    	});
    }
}
