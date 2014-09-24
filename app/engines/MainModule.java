package engines;

import services.chipher.CipherService;
import services.chipher.CipherServiceImpl;
import services.conf.ConfigurationService;
import services.conf.ConfigurationServiceImpl;
import services.http.HttpService;
import services.http.HttpServiceImpl;
import services.weather.WeatherService;
import services.weather.WeatherServiceImpl;

import com.google.inject.AbstractModule;
import com.google.inject.internal.Errors;

import controllers.*;
import engines.forecastholder.ForecastHolderEngine;
import engines.forecastholder.ForecastHolderEngineImpl;
import engines.ping.*;
import engines.pool.PoolEngine;
import engines.pool.PoolEngineImpl;
import engines.weatherfetcher.WeatherFetcherEngine;
import engines.weatherfetcher.WeatherFetcherEngineImpl;

/**
 * Configure all binding for the production application
 * Another module is used for the unit tests
 * @author Jerome Baudoux
 */
public class MainModule extends AbstractModule {

	@Override
	protected void configure() {
		
		// Controller
		bind(Api.class);
		bind(Application.class);
		bind(Errors.class);

		// Engines
		bind(PoolEngine.class).to(PoolEngineImpl.class);
		bind(PingEngine.class).to(PingEngineImpl.class);
		bind(WeatherFetcherEngine.class).to(WeatherFetcherEngineImpl.class);
		bind(ForecastHolderEngine.class).to(ForecastHolderEngineImpl.class);

		// Services
		bind(HttpService.class).to(HttpServiceImpl.class);
		bind(WeatherService.class).to(WeatherServiceImpl.class);
		bind(CipherService.class).to(CipherServiceImpl.class);
		bind(ConfigurationService.class).to(ConfigurationServiceImpl.class);
		bind(ConfigurationService.class).to(ConfigurationServiceImpl.class);
	}
}
