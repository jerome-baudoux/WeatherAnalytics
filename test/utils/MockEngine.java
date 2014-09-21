package utils;

import engines.Engine;
import engines.ping.PingEngine;

/**
 * A mock engine that does nothing
 * @author Jerome Baudoux
 */
public class MockEngine implements Engine, PingEngine {

	@Override
	public void start() {}

	@Override
	public void stop() {}
}
