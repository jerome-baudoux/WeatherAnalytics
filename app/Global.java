import com.google.inject.Injector;

import play.Application;
import play.GlobalSettings;
import play.libs.F.Promise;
import play.mvc.Result;
import play.mvc.Http.RequestHeader;
import controllers.Errors;
import engine.MainEngine;

/**
 * Global Play configuration
 * @author Jerome Baudoux
 */
public class Global extends GlobalSettings {
	
	/**
	 * Main engine of the application
	 * Used for everything that is not Views/Controllers
	 */
	protected final MainEngine mainEngine = new MainEngine();
	
	/**
	 * @return get the Guice injector
	 */
	public Injector getInjector() {
		return this.mainEngine.getInjector();
	}

	@Override
	public void onStart(Application app) {
		super.onStart(app);
		this.mainEngine.start();
	}
	
	@Override
	public void onStop(Application app) {
		super.onStop(app);
		this.mainEngine.stop();
	}

	@Override
	public Promise<Result> onBadRequest(RequestHeader header, String error) {
		return Promise.promise(() -> Errors.message(error));
	}

	@Override
	public Promise<Result> onError(RequestHeader header, Throwable error) {
		return Promise.promise(() -> Errors.message(error.getMessage()));
	}

	@Override
	public Promise<Result> onHandlerNotFound(RequestHeader header) {
		return Promise.promise(() -> Errors.missingFile());
	}
}
