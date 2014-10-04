package integration;

import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import dao.WeatherDayDocumentDaoImpl;
import engines.MainEngine;
import play.libs.Json;
import play.test.TestBrowser;
import utils.AbstractTest;
import static org.fest.assertions.Assertions.*;

/**
 * A base class for test Integration
 * @author Jerome Baudoux
 */
public abstract class AbstractIntegrationTest extends AbstractTest {
	
	private static final int PORT = 3333;
	private static final String HOSTNAME = "http://localhost";
	
	/**
	 * Global rules for tests
	 * @return global module for the test
	 */
	public AbstractModule getTestModules() {
		return new IntegrationTestModule();
	}
	
	/**
	 * Default constructor
	 */
	public AbstractIntegrationTest() {
		super();
	}
	
	/**
	 * Fetch Json from browser
	 * @param browser current browser
	 * @param expected expected object
	 * @return object
	 */
	public static <A> A getJson(TestBrowser browser, Class<A> expected) {
		assertThat(browser.pageSource()).isNotNull().isNotEmpty();
		return Json.fromJson(Json.parse(browser.pageSource()), expected);
	}
	
	public void runTest(final TestInit initializer) {
		runTestBrowser(null, initializer, null);
	}

	public void runTestBrowser(final String route, final BrowserCallback callback) {
		runTestBrowser(route, ()->{}, callback);
	}
	
	/**
	 * Run the test and call our function
	 * @param route route to test
	 * @param callback function to call
	 */
	public void runTestBrowser(final String route, final TestInit initializer, final BrowserCallback callback) {
        running(testServer(PORT, fakeApplication(inMemoryDatabase(), new MainEngine(getInjector()))), HTMLUNIT, (TestBrowser browser) -> {
        	
        	// Call init
        	initializer.init();
        	
        	// Call browser
        	if(route!=null && callback!=null) {
				browser.goTo(HOSTNAME+":"+PORT+"/"+route);
				callback.test(browser);
        	}
        });
	}
	
	/**
	 * A simple interface to initialize tests
	 * @author Jerome Baudoux
	 */
	interface TestInit {
		public void init();
	}

	/**
	 * A simple interface for testing
	 * @author Jerome Baudoux
	 */
	interface BrowserCallback {
		public void test(TestBrowser browser);
	}
	
	/**
	 * Specific DAO for H2 Database
	 * @author Jerome Baudoux
	 *
	 */
	@Singleton
	public static class WeatherDayDocumentDaoImplH2 extends WeatherDayDocumentDaoImpl {

		/**
		 * Get the SQL date from a String
		 * @param date string date
		 * @return sql date
		 */
		protected String getDate(String date) {
			return "'"+date+"'";
		}
	}
}
