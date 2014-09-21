package engines;

import services.http.HttpService;
import services.http.HttpServiceImpl;

import com.google.inject.AbstractModule;
import com.google.inject.internal.Errors;

import controllers.*;
import engines.ping.*;

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
		bind(HttpService.class).to(HttpServiceImpl.class);
	}
}
