package controllers;

import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Application extends Controller {
	
	public static Result index() {
		return ok("Work in progress");
	}
	
    public static Result dev(String name) {
        String fileName;
        try {
            fileName = URLDecoder.decode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            fileName = name;
        }
        File file = new File("ui/app/" + fileName);
        try {
			return ok(file, true);
		} catch(Throwable t) {
			return notFound();
		}
    }
}
