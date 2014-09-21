package application;

import org.junit.Test;

import com.google.inject.AbstractModule;

import engines.MainEngine;
import engines.weatherfetcher.WeatherFetcherEngine;
import engines.weatherfetcher.WeatherFetcherEngineImpl;
import utils.AbstractTest;

public class TestInjectionProd extends AbstractTest {

	/**
	 * Adds engines and service that can start without play
	 */
	public AbstractModule getSpecificModules() {
		return new AbstractModule() {
			@Override
			protected void configure() {
				bind(WeatherFetcherEngine.class).to(WeatherFetcherEngineImpl.class);
			}
		};
	}
	
	/**
	 * Tests that the server is able to start
	 */
    @Test
    public void testRun() {
    	MainEngine engine = new MainEngine(getInjector());
    	engine.start();
    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// Slient
		}
    	engine.stop();
    }
}
