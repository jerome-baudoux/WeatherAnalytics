package engine;

import com.google.inject.AbstractModule;
import com.google.inject.internal.Errors;

import controllers.*;
import engine.pingengine.*;

/**
 * Configure all binding for the production application
 * Another module is used for the unit tests
 * @author Jerome Baudoux
 */
public class MainModule extends AbstractModule {

	@Override
	protected void configure() {
		
		// Controller
		bind(Api.class);
		bind(Application.class);
		bind(Errors.class);

		// Engines
		bind(PingEngine.class).to(PingEngineImpl.class);

		// Services
		
	}
}
