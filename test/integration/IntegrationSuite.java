package integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite for all integration test
 * 
 * Important, before running this test, you have to define the following environment variable:
 * DATABSE_URL = WHATEVER
 * 
 * @author Jerome Baudoux
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestApiPing.class,
	TestApiWeatherCities.class,
	TestApiWeatherForecast.class,
	TestApiWeatherHistory.class,
	TestServiceCipher.class,
	TestServiceConfig.class
})
public class IntegrationSuite {
	// Nothing to do
}
