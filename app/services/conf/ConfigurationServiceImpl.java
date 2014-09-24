package services.conf;

import play.Play;

/**
 * A service to fetch configuration values
 * @author Jerome Baudoux
 */
public class ConfigurationServiceImpl implements ConfigurationService {

	/**
	 * Fetch String from environment variable
	 * @param var var name
	 * @param defaultValue default value
	 * @return String value
	 */
	@Override
	public String getEnvVariable(String var, String defaultValue) {
		String value = System.getenv(var);
		if(value==null) {
			return defaultValue;
		}
		return value;
	}
	
	/**
	 * Fetch String
	 * @param var var name
	 * @param defaultValue default value
	 * @return String value value
	 */
	@Override
	public String getString(String var, String defaultValue) {
		return Play.application().configuration().getString(var, defaultValue);
	}
	
	/**
	 * Fetch Int
	 * @param var var name
	 * @param defaultValue default value
	 * @return Int value value
	 */
	@Override
	public Integer getInt(String var, int defaultValue) {
		return Play.application().configuration().getInt(var, defaultValue);
	}
	
	/**
	 * Fetch Long
	 * @param var var name
	 * @param defaultValue default value
	 * @return Long value value
	 */
	@Override
	public Long getLong(String var, long defaultValue) {
		return Play.application().configuration().getLong(var, defaultValue);
	}
}
