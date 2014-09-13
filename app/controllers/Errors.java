package controllers;

import play.mvc.Controller;
import play.mvc.Result;

import views.html.*;

/**
 * Handle errors on the back end side
 * @author Jerome Baudoux
 */
public class Errors extends Controller {

	/**
	 * @return show error page
	 */
	public static Result unknown() {
		return ok(errors.render("Internal Error", "Something went wrong... That's all we know..."));
	}

	/**
	 * @return show error page
	 */
	public static Result missingFile() {
		return ok(errors.render("Error 404", "File not found"));
	}

	/**
	 * @return show error page
	 */
	public static Result message(String message) {
		return ok(errors.render("Internal Error", "Something went wrong: " + message));
	}
}
