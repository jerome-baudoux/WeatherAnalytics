import com.google.inject.Guice;
import com.google.inject.Injector;

import play.Application;
import play.GlobalSettings;
import play.libs.F.Promise;
import play.mvc.Result;
import play.mvc.Http.RequestHeader;
import controllers.Errors;
import engine.MainEngine;
import engine.MainModule;

/**
 * Global Play configuration
 * @author Jerome Baudoux
 */
public class Global extends GlobalSettings {

	/**
	 * Injector for dependency injection
	 */
	protected final Injector injector; 
	
	/**
	 * Main engine of the application
	 * Used for everything that is not Views/Controllers
	 */
    protected final MainEngine mainEngine;
    
    /**
     * Create global configuration
     */
    public Global() {
    	this.injector = Guice.createInjector(new MainModule()); 
    	this.mainEngine = new MainEngine(injector);
    }

    /**
     * Each startup
     */
	@Override
	public void onStart(Application app) {
		super.onStart(app);
		this.mainEngine.start();
	}
	
	/**
	 * Each shutdown
	 */
	@Override
	public void onStop(Application app) {
		super.onStop(app);
		this.mainEngine.stop();
	}

	/**
	 * On String error
	 */
	@Override
	public Promise<Result> onBadRequest(RequestHeader header, String error) {
		return Promise.promise(() -> Errors.message(error));
	}

	/**
	 * On Throwable error
	 */
	@Override
	public Promise<Result> onError(RequestHeader header, Throwable error) {
		return Promise.promise(() -> Errors.message(error.getMessage()));
	}

	/**
	 * When no handler is found
	 */
	@Override
	public Promise<Result> onHandlerNotFound(RequestHeader header) {
		return Promise.promise(() -> Errors.missingFile());
	}

	/**
	 * Instrument controller to allow Guice injection 
	 */
    @Override
    public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
    	return this.injector.getInstance(controllerClass);
    }
	
	/**
	 * @return get the Guice injector
	 */
	public Injector getInjector() {
		return this.injector;
	}
}
