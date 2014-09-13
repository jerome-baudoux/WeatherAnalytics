import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import play.Application;
import play.GlobalSettings;
import play.api.mvc.AnyContent;
import play.api.mvc.Handler;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Http.Request;
import play.mvc.Http.RequestHeader;

import controllers.Errors;

/**
 * Global Play configuration
 * @author Jerome Baudoux
 */
public class Global extends GlobalSettings {
	
	@Override
	public void onStart(Application arg0) {
		super.onStart(arg0);
	}

	@Override
	public Promise<Result> onBadRequest(RequestHeader header, String error) {
		return Promise.promise(() -> Errors.message(error));
	}

	@Override
	public Promise<Result> onError(RequestHeader header, Throwable error) {
		return Promise.promise(() -> Errors.message(error.getMessage()));
	}

	@Override
	public Promise<Result> onHandlerNotFound(RequestHeader header) {
		return Promise.promise(() -> Errors.missingFile());
	}
}
