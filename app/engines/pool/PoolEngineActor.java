package engines.pool;

import engines.pool.PoolEngine.Task;
import play.Logger;
import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

/**
 * An actor that runs the the tasks
 * @author Jerome Baudoux
 */
public class PoolEngineActor extends AbstractActor {

	/**
	 * Builds rules
	 */
	public PoolEngineActor() {
		try {
			receive(ReceiveBuilder.match(Task.class, (Task t) -> {
				Logger.trace("A task is launched from actor: " + this);
				try {
					t.run();
				} catch(Throwable e) {
					Logger.error(e.getMessage(), t);
				}
			}).matchAny(o -> Logger.error("Unknown message recieved")).build());
		} catch(Throwable t) {
			Logger.error(t.getMessage(), t);
		}
	}
}
