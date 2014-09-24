package integration;

import utils.MockEngine;

import com.google.inject.AbstractModule;

import engines.ping.PingEngine;
import engines.weatherfetcher.WeatherFetcherEngine;

/**
 * Configure all binding for the test application
 * @author Jerome Baudoux
 */
public class IntegrationTestModule extends AbstractModule {

	@Override
	protected void configure() {
		
		// Engines
		bind(PingEngine.class).to(MockEngine.class);
		bind(WeatherFetcherEngine.class).to(MockEngine.class);
	}
}
