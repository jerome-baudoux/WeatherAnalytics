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
		receive(ReceiveBuilder.match(Task.class, (Task t) -> {
			Logger.trace("A task is launched from actor: " + this);
			t.run();
		}).matchAny(o -> Logger.error("Unknown message recieved")).build());
	}
}
