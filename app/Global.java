import com.google.inject.Guice;
import com.google.inject.Stage;

import engines.MainEngine;
import engines.MainModule;

/**
 * Global Play configuration
 * @author Jerome Baudoux
 */
public class Global extends MainEngine {

	public Global() {
		super(Guice.createInjector(Stage.PRODUCTION, new MainModule()));
	}
}
