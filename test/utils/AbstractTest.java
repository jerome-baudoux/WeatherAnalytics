package utils;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.util.Modules;

import engines.MainModule;

/**
 * A base class for tests
 * @author Jerome Baudoux
 */
public abstract class AbstractTest {
	
	/**
	 * Injector
	 */
	public Injector injector;
	
	/**
	 * Default constructor
	 */
	public AbstractTest() {
		this.injector = createInjector();
	}

	/**
	 * Global rules for tests
	 * @return global module for the test
	 */
	public abstract AbstractModule getTestModules();
	
	/**
	 * Method to override in each tests if you want different bindings
	 * @return specific module for the test
	 */
	public AbstractModule getSpecificModules() {
		return new AbstractModule() {
			@Override
			protected void configure() {}
		};
	}
	
	/**
	 * @return Guice injector
	 */
	public Injector getInjector() {
		return injector;
	}
	
	/**
	 * Creates the Injector for the current test
	 * @return Guice Injector
	 */
	protected Injector createInjector() {
		return Guice.createInjector(Stage.DEVELOPMENT, 
			Modules.override(
					Modules.override(new MainModule()).with(getTestModules())
			).with(getSpecificModules())
		);
	}

}
