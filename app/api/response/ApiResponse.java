package api.response;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.libs.Json;

/**
 * A abstract base response object for API
 * @author Jerome Baudoux
 */
@JsonInclude(Include.NON_NULL)
public abstract class ApiResponse<T extends ApiResponse<T>> {

	/**
	 * Result of the task
	 */
	protected int result;
	
	/**
	 * Description of what happened
	 */
	protected String message;
	
	/**
	 * If an error occurred, the stack trace should be there
	 */
	protected Throwable error;
	
	/**
	 * Empty object
	 */
	public ApiResponse() {
		this.result = ApiResultCode.UNKNOWN.getCode();
	}
	
	/**
	 * Converts the current response to json
	 */
	public JsonNode toJSON() {
		return Json.toJson(this);
	}
	
	/**
	 * Converts the current response to pretty json
	 * @return pretty json
	 * @throws JsonProcessingException
	 */
	public String toPrettyJSON() throws JsonProcessingException {
		return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
	}

	/**
	 * Check {@link ApiResultCode} for more information
	 * @return A code representing the result of the task.
	 */
	public int getResult() {
		return result;
	}

	/**
	 * Check {@link ApiResultCode} for more information
	 * @param result A code representing the result of the task.
	 */
	@SuppressWarnings("unchecked")
	public T setResult(int result) {
		this.result = result;
		return (T)this;
	}

	/**
	 * @return Description of what happened
	 */
	@Nullable
	public String getMessage() {
		return message;
	}

	/**
	 * @param message Description of what happened
	 */
	@SuppressWarnings("unchecked")
	public T setMessage(String message) {
		this.message = message;
		return (T)this;
	}

	/**
	 * @return If an error occurred, the stack trace should be there
	 */
	@Nullable
	public Throwable getError() {
		return error;
	}

	/**
	 * @param error If an error occurred, the stack trace should be there
	 */
	@SuppressWarnings("unchecked")
	public T setError(Throwable error) {
		this.error = error;
		return (T)this;
	}
}
