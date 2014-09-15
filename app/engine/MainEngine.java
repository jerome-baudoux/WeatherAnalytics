package engine;

import java.util.LinkedList;
import java.util.List;

import com.google.inject.Injector;

import engine.pingengine.PingEngine;

/**
 * Main engine for the application
 * @author Jerome Baudoux
 */
public class MainEngine implements Engine {

	protected final List<Engine> engines;
	
	/**
	 * Main engine creation
	 * @param injector Guice Injector
	 */
	public MainEngine(Injector injector) {
		this.engines = new LinkedList<Engine>();
		this.engines.add(injector.getInstance(PingEngine.class));
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
