package application;

import org.junit.Test;

import engines.MainEngine;

public class TestInjectionProd extends AbstractApplicationTest {
	
	/**
	 * Tests that the server is able to start
	 */
    @Test
    public void testRun() {
    	MainEngine engine = new MainEngine(getInjector());
    	engine.start();
    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// Slient
		}
    	engine.stop();
    }
}
