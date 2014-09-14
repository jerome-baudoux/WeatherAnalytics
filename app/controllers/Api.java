package controllers;

import api.ApiResultConstants;
import api.SimpleApiResponse;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * A controller for all public API
 * @author Jerome Baudoux
 */
public class Api extends Controller {

	/**
	 * For debug purpose only
	 * @return Work In Progress
	 */
	public static Result wip() {
		return ok(
				new SimpleApiResponse()
				.setResult(ApiResultConstants.SUCCESS)
				.setMessage("Work In Progress")
				.toJSON()
		);
	}

	/**
	 * When an unknown API was called
	 * @param name name of the api
	 * @return 404 error
	 */
	public static Result apiNotFound(String name) {
		return notFound(
				new SimpleApiResponse()
					.setResult(ApiResultConstants.ERROR_API_NOT_FOUND)
					.setMessage("No API found for the name: " + name)
					.toJSON()
		);
	}
}
