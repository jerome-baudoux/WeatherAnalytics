package controllers;

import play.mvc.Controller;
import play.mvc.Result;

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
     * 
     * @param path
     * @param resource
     * @param allowsInProd
     * @return
     */
    public static Result dev(String path, String resource) {
    	
    	if(play.api.Play.isProd(play.api.Play.current())) {
        	// If in production
        	// do not serve the resource that does not exist anyway
        	return Errors.missingFile();
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
			return Errors.missingFile();
		}
    }
}
