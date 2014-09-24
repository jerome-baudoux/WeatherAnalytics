package integration;

import services.conf.ConfigurationService;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

/**
 * Test the Ping API
 * @author Jerome Baudoux
 */
public class TestServiceConfig extends AbstractIntegrationTest {

	/**
	 * Try int variables
	 */
    @Test
    public void testInt() {
    	runTest(() -> {
    		// Does not default
    		assertThat(getInjector().getInstance(
    				ConfigurationService.class).getInt("DOES_NOT_EXISTS", 5)).isEqualTo(5);
    		
    		// Exists
    		assertThat(getInjector().getInstance(
    				ConfigurationService.class).getInt(ConfigurationService.PING_FREQUENCY, 0)).isEqualTo(5);
    	});
    }

	/**
	 * Try long variables
	 */
    @Test
    public void testLong() {
    	runTest(() -> {
    		// Does not default
    		assertThat(getInjector().getInstance(
    				ConfigurationService.class).getLong("DOES_NOT_EXISTS", 5L)).isEqualTo(5L);
            
    		// Exists
    		assertThat(getInjector().getInstance(
    				ConfigurationService.class).getLong(ConfigurationService.PING_FREQUENCY, 0)).isEqualTo(5L);
    	});
    }

	/**
	 * Try String variables
	 */
    @Test
    public void testString() {
    	runTest(() -> {
    		// Does not default
    		assertThat(getInjector().getInstance(
    				ConfigurationService.class).getString("DOES_NOT_EXISTS", "DEFAULT")).isEqualTo("DEFAULT");
            
    		// Exists
    		assertThat(getInjector().getInstance(
    				ConfigurationService.class).getString(ConfigurationService.WORKER_POOL_SYSTEM_NAME, "")).isEqualTo("PoolEngineImplSystem");
    	});
    }
}
