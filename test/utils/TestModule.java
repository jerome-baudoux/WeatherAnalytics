package utils;

import com.google.inject.AbstractModule;

import engine.pingengine.PingEngine;

/**
 * Configure all binding for the test application
 * @author Jerome Baudoux
 */
public class TestModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PingEngine.class).to(MockEngine.class);
	}
}
