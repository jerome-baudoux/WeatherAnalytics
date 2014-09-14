package engine;

import java.util.Arrays;
import java.util.List;

import engine.pingengine.PingEngine;

/**
 * Main engine for the application
 * @author Jerome Baudoux
 */
public class MainEngine implements Engine {
	
	protected final List<Engine> engines;
	
	/**
	 * Main engine creation
	 */
	public MainEngine() {
		this.engines = Arrays.asList(
			new PingEngine()
		);
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