package engine;

import com.google.inject.AbstractModule;

import engine.pingengine.PingEngine;
import engine.pingengine.PingEngineImpl;

/**
 * Configure all binding for the production application
 * Another module is used for the unit tests
 * @author Jerome Baudoux
 */
public class MainModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PingEngine.class).to(PingEngineImpl.class);
	}
}
