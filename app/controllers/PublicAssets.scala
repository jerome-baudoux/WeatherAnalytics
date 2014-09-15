package controllers

import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Controller that handles fetching elements from the public assets
 */
object PublicAssets extends Controller {

  /**
   * Fetch the production file.
   * If an error occurs, show a customized error page
   */
  def at(path:String, file:String) = Action.async { implicit request =>
    Assets.at(path, file, false).apply(request).map(result => {
			// No error just send the result
			if(result.header.status < 400) {
				result
			}
			// Specific 404 error
			else if(result.header.status == 404) {
				NotFound(views.html.errors(Errors.TITLE_ERROR_404)(Errors.MESSAGE_ERROR_404))
			}
			// Other kind of errors
			else {
				InternalServerError(views.html.errors(Errors.TITLE_ERROR_500)(Errors.MESSAGE_ERROR_500))
			}
		})
  }
}