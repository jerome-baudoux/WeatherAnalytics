package services.http;

/**
 * A service that is able to do HTTP request and provide the result asynchronously
 * @author Jerome Baudoux
 */
public interface HttpService {

	/**
	 * Simple GET
	 * @param url URL to query
	 * @param success called in case of success
	 * @param error called in case of error
	 */
	public void get(String url, Success success, Error error);
	
	/**
	 * Success handler
	 * @author Jerome
	 */
	public interface Success {
		void success(final String body, final int status);
	}
	
	/**
	 * Error handler
	 * @author Jerome
	 */
	public interface Error {
		void error(final String body, final int status, final Throwable t);
	}
}
