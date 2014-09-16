package integration;

import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import engine.MainEngine;
import play.libs.Json;
import play.test.TestBrowser;
import utils.AbstractTest;
import static org.fest.assertions.Assertions.*;

/**
 * A base class for test Integration
 * @author Jerome Baudoux
 */
public class AbstractIntegrationTest extends AbstractTest {
	
	private static final int PORT = 3333;
	private static final String HOSTNAME = "http://localhost";
	
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
	
	/**
	 * Run the test and call our function
	 * @param route route to test
	 * @param callback function to call
	 */
	public void runTest(final String route, final BrowserCallback callback) {
        running(testServer(3333, fakeApplication(inMemoryDatabase(), new MainEngine(getInjector()))), HTMLUNIT, (TestBrowser browser) -> {
			browser.goTo(HOSTNAME+":"+PORT+"/"+route);
			callback.test(browser);
        });
	}

	/**
	 * A simple interface for testing
	 * @author Jerome Baudoux
	 */
	interface BrowserCallback {
		public void test(TestBrowser browser);
	}
}
