package engine;

import java.util.LinkedList;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

import engine.pingengine.PingEngine;

/**
 * Main engine for the application
 * @author Jerome Baudoux
 */
public class MainEngine implements Engine {

	protected final Injector injector;
	protected final List<Engine> engines;
	
	/**
	 * Main engine creation
	 */
	public MainEngine() {
		
		// Guice Injector
		this.injector = Guice.createInjector(Stage.PRODUCTION, new MainModule());
		
		// Prepare all engines
		this.engines = new LinkedList<Engine>();
		this.engines.add(this.injector.getInstance(PingEngine.class));
	}
	
	/**
	 * @return Get the Guice injector 
	 */
	public Injector getInjector() {
		return injector;
	}

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
}
