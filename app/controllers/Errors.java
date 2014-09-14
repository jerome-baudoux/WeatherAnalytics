package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * Handle errors on the back end side
 * @author Jerome Baudoux
 */
public class Errors extends Controller {

	public static String TITLE_ERROR_404 = "Error 404";
	public static String TITLE_ERROR_500 = "Internal Error";

	public static String MESSAGE_ERROR_404 = "File not found";
	public static String MESSAGE_ERROR_500_WITH_TEXT = "Something went wrong: ";
	public static String MESSAGE_ERROR_500 = "Something went wrong... That's all we know...";

	/**
	 * @return show error page
	 */
	public static Result missingFile() {
		return notFound(views.html.errors.render(TITLE_ERROR_404, MESSAGE_ERROR_404));
	}

	/**
	 * @return show error page
	 */
	public static Result message(String message) {
		return internalServerError(views.html.errors.render(MESSAGE_ERROR_500, MESSAGE_ERROR_500_WITH_TEXT + message));
	}
	
	/**
	 * @return show error page
	 */
	public static Result unknown() {
		return internalServerError(views.html.errors.render(TITLE_ERROR_500, MESSAGE_ERROR_500));
	}
}
