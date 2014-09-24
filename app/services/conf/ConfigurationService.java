package services.conf;

/**
 * A service to fetch configuration values
 * @author Jerome Baudoux
 */
public interface ConfigurationService {

	public static String PING_URL = "ping.url";
	public static String PING_FREQUENCY = "ping.frequency.minutes";

	public static String WORKER_POOL_SYSTEM_NAME = "worker.pool.system.name";
	public static String WORKER_POOL_SIZE = "worker.pool.size";

	/**
	 * Fetch String from environment variable
	 * @param var var name
	 * @param defaultValue default value
	 * @return String value
	 */
	String getEnvVariable(final String var, String defaultValue);

	/**
	 * Fetch String
	 * @param var var name
	 * @param defaultValue default value
	 * @return String value
	 */
	String getString(String var, String defaultValue);

	/**
	 * Fetch Int
	 * @param var var name
	 * @param defaultValue default value
	 * @return String value
	 */
	Integer getInt(String var, int defaultValue);
	
	/**
	 * Fetch Long
	 * @param var var name
	 * @param defaultValue default value
	 * @return String value
	 */
	Long getLong(String var, long defaultValue);
}
