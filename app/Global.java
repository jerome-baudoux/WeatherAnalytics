import play.Application;
import play.GlobalSettings;
import play.libs.F.Promise;
import play.mvc.Result;
import play.mvc.Http.RequestHeader;
import controllers.Errors;
import engine.Engine;
import engine.MainEngine;

/**
 * Global Play configuration
 * @author Jerome Baudoux
 */
public class Global extends GlobalSettings {
	
	protected final Engine mainEngine = new MainEngine();
	
	@Override
	public void onStart(Application arg) {
		super.onStart(arg);
		this.mainEngine.start();
	}
	
	@Override
	public void onStop(Application arg) {
		super.onStop(arg);
		this.mainEngine.stop();
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
