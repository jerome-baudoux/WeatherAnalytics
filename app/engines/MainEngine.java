package engines;

import java.util.LinkedList;
import java.util.List;

import play.Application;
import play.GlobalSettings;
import play.libs.F.Promise;
import play.mvc.Result;
import play.mvc.Http.RequestHeader;

import com.google.inject.Injector;

import controllers.Errors;
import engines.forecastholder.ForecastHolderEngine;
import engines.ping.PingEngine;
import engines.pool.PoolEngine;
import engines.weatherfetcher.WeatherFetcherEngine;

/**
 * Main engine for the application
 * @author Jerome Baudoux
 */
public class MainEngine extends GlobalSettings implements Engine {
	
	/**
	 * Injector for dependency injection
	 */
	protected Injector injector; 

	/**
	 * All engines of the application
	 */
	protected List<Engine> engines;
	
	/**
	 * Main engine creation
	 * @param injector Guice Injector
	 */
	public MainEngine(Injector injector) {
		this.injector = injector;
		this.engines = new LinkedList<>();
		this.engines.add(injector.getInstance(PoolEngine.class));
		this.engines.add(injector.getInstance(PingEngine.class));
		this.engines.add(injector.getInstance(ForecastHolderEngine.class));
		this.engines.add(injector.getInstance(WeatherFetcherEngine.class));
	}
	
	/*
	 * Engine
	 */

	/**
	 * Start main engine
	 */
	@Override
	public synchronized void start() {
		for(Engine engine: this.engines) {
			engine.start();
		}
	}

	/**
	 * Stop main engine
	 */
	@Override
	public synchronized void stop() {
		for(Engine engine: this.engines) {
			engine.stop();
		}
	}
	
	/*
	 * Settings
	 */

    /**
     * Each startup
     */
	@Override
	public void onStart(Application app) {
		super.onStart(app);
		this.start();
	}
	
	/**
	 * Each shutdown
	 */
	@Override
	public void onStop(Application app) {
		super.onStop(app);
		this.stop();
	}

	/**
	 * On String error
	 */
	@Override
	public Promise<Result> onBadRequest(RequestHeader header, String error) {
		return Promise.promise(() -> getInjector().getInstance(Errors.class).message(error));
	}

	/**
	 * On Throwable error
	 */
	@Override
	public Promise<Result> onError(RequestHeader header, Throwable error) {
		return Promise.promise(() -> getInjector().getInstance(Errors.class).message(error.getMessage()));
	}

	/**
	 * When no handler is found
	 */
	@Override
	public Promise<Result> onHandlerNotFound(RequestHeader header) {
		return Promise.promise(() -> getInjector().getInstance(Errors.class).missingFile());
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
