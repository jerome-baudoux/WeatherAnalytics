package controllers;

import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Main controller for the application
 * @author Jerome Baudoux
 */
@Singleton
public class Application extends Controller {
	
	/**
	 * We need to delegate in case of errors
	 */
	protected Errors errorController;
	
	/**
	 * For dependency injection
	 * @param errorController We need to delegate in case of errors
	 */
	@Inject
	public Application(Errors errorController) {
		this.errorController = errorController;
	}

    /**
     * Remove last slash, because /resource is not the same as /resource/
     * and we want the two to work without redefining every route twice
     * @param file path to redirect
     * @return Redirection
     */
    public Result redirectToResource(String file) {
    	return redirect("/"+file);
    }
    
    /**
     * Fetch ui files for DEV, returns 404 in PROD
     * @param path folder
     * @param resource file
     * @return file or 404
     */
    public Result dev(String path, String resource) {
    	
    	if(play.api.Play.isProd(play.api.Play.current())) {
        	// If in production
        	// do not serve the resource that does not exist anyway
        	return this.errorController.missingFile();
    	}
    	
    	// Only available in DEV
        String fileName;
        try {
            fileName = URLDecoder.decode(resource, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            fileName = resource;
        }
        File file = new File(path + "/" + fileName);
        try {
			return ok(file, true);
		} catch(Throwable t) {
			return this.errorController.missingFile();
		}
    }
}
