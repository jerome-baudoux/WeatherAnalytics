package utils;

import engines.Engine;
import engines.ping.PingEngine;
import engines.weatherfetcher.WeatherFetcherEngine;

/**
 * A mock engine that does nothing
 * @author Jerome Baudoux
 */
public class MockEngine implements Engine, PingEngine, WeatherFetcherEngine {

	@Override
	public void start() {}

	@Override
	public void stop() {}
}
