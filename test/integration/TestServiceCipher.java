package integration;

import services.chipher.CipherService;

import org.junit.*;

import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;

/**
 * Test the Ping API
 * @author Jerome Baudoux
 */
public class TestServiceCipher extends AbstractIntegrationTest {

	/**
	 * Try cipher
	 */
    @Test
    public void testEncrypt() {
    	runTest(() -> {
    		try {
				assertThat(getInjector().getInstance(CipherService.class).encrypt("FOO")).isEqualTo("Gnkgm8P8p6GkrsG6L1BF6Q==");
			} catch (Exception e) {
				fail(e.getMessage());
			}
    	});
    }

	/**
	 * Try decipher
	 */
    @Test
    public void testDecrypt() {
    	runTest(() -> {
    		try {
				assertThat(getInjector().getInstance(CipherService.class).decrypt("Gnkgm8P8p6GkrsG6L1BF6Q==")).isEqualTo("FOO");
			} catch (Exception e) {
				fail(e.getMessage());
			}
    	});
    }
}
