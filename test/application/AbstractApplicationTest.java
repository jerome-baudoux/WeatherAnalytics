package application;

import com.google.inject.AbstractModule;

import services.conf.ConfigurationService;
import utils.AbstractTest;

/**
 * A base class for test Integration
 * @author Jerome Baudoux
 */
public abstract class AbstractApplicationTest extends AbstractTest {

	/**
	 * Global rules for tests
	 * @return global module for the test
	 */
	public AbstractModule getTestModules() {
		return new ApplicationTestModule();
	}
	
	/**
	 * Default constructor
	 */
	public AbstractApplicationTest() {
		super();
	}
	
	/**
	 * Mock configuration for integration tests
	 * @author Jerome Baudoux
	 */
	public static class MockConfigurationService implements ConfigurationService {

		@Override
		public String getEnvVariable(String var, String defaultValue) {
			return defaultValue;
		}

		@Override
		public String getString(String var, String defaultValue) {
			return defaultValue;
		}

		@Override
		public Integer getInt(String var, int defaultValue) {
			return defaultValue;
		}

		@Override
		public Long getLong(String var, long defaultValue) {
			return defaultValue;
		}
	}
}
