package controllers;

import play.mvc.Controller;
import play.mvc.Result;

import utils.Variables;

import views.html.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Main controller for the application
 * @author Jerome Baudoux
 */
public class Application extends Controller {

    /**
     * Remove last slash, because /resource is not the same as /resource/
     * and we want the two to work without redefining every route twice
     * @param file path to redirect
     * @return Redirection
     */
    public static Result redirectToResource(String file) {
    	return redirect("/"+file);
    }
	
	/**
	 * Serves the interface in a development environment
	 * @param resource name of the resource 
	 * @return dev interface
	 */
    public static Result dev(String resource) {
    	
    	// Only available in DEV
    	if(play.api.Play.isProd(play.api.Play.current())) {
    		
            String fileName;
            try {
                fileName = URLDecoder.decode(resource, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                fileName = resource;
            }
            File file = new File(Variables.PATH_TO_DEV_UI + fileName);
            try {
    			return ok(file, true);
    		} catch(Throwable t) {
    			return missingFile();
    		}
    		
    	} 
    	
    	// If in production
    	// do not serve the resource that does not exist anyway
    	return missingFile();
    }

	/**
	 * @return show error page
	 */
	public static Result error() {
		return ok(errors.render("Internal Error", "Something went wrong"));
	}

	/**
	 * @return show error page
	 */
	public static Result missingFile() {
		return ok(errors.render("Error 404", "File not found"));
	}
}
