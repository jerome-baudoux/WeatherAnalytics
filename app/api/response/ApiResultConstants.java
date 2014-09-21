package api.response;

public enum ApiResultConstants {

	/*
	 * Bellow 1000, all generic messages
	 */
	UNKNOWN(0),

	SUCCESS(1),
	PROCESSING(2),
	
	ERROR_UNKNOWN(100),
	ERROR_API_NOT_FOUND(110),
	ERROR_PARAMETER_MISSING(120),
	ERROR_PARAMETER_WRONG_VALUE(121);
	
	/*
	 * Under 1000, specific messages
	 */

	private final int code;
	
	private ApiResultConstants(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
