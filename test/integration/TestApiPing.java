package integration;

import org.junit.*;

import api.response.ApiResultCode;
import api.response.SimpleApiResponse;
import play.test.TestBrowser;
import static org.fest.assertions.Assertions.*;

/**
 * Test the Ping API
 * @author Jerome Baudoux
 *
 */
public class TestApiPing extends AbstractIntegrationTest {

	/**
	 * Do a ping
	 * Expected result -> API OK 1
	 */
    @Test
    public void testPingOk() {
    	runTest("api/ping", (TestBrowser browser) -> {
            SimpleApiResponse response = getJson(browser, SimpleApiResponse.class);
            assertThat(response.getResult()).isEqualTo(ApiResultCode.SUCCESS.getCode());
    	});
    }

    /**
     * DO a ping/wrong/name
     * Expected result -> No SUCH API 110
     */
    @Test
    public void testPingErrorInName() {
    	runTest("api/ping/wrong/name", (TestBrowser browser) -> {
            SimpleApiResponse response = getJson(browser, SimpleApiResponse.class);
            assertThat(response.getResult()).isEqualTo(ApiResultCode.ERROR_API_NOT_FOUND.getCode());
    	});
    }
}
