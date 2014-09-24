package engines.pool;

import java.util.concurrent.TimeUnit;

import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.AbstractFunction0;
import scala.runtime.BoxedUnit;
import services.conf.ConfigurationService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.SmallestMailboxPool;

/**
 * A pool engine that allows to run tasks in a pool
 * @author Jerome Baudoux
 */
@Singleton
public class PoolEngineImpl implements PoolEngine {
	
	protected static final String SYSTEM_NAME_DEFAULT = "PoolEngineImplSystem";
	protected static final int POOL_SIZE_DEFAULT = 10;
	
	protected ConfigurationService config;
	
	protected ActorSystem system;
	protected ActorRef mainActor;
	protected ExecutionContext context;
	
	@Inject
	public PoolEngineImpl(final ConfigurationService config) {
		this.config = config;
	}

	/**
	 * On Startup
	 */
	@Override
	public void start() {

		this.system = ActorSystem.create(
				this.config.getString(ConfigurationService.WORKER_POOL_SYSTEM_NAME, SYSTEM_NAME_DEFAULT));
		
		this.mainActor = this.system.actorOf(new SmallestMailboxPool(
				this.config.getInt(ConfigurationService.WORKER_POOL_SIZE,
						POOL_SIZE_DEFAULT)).props(Props.create(PoolEngineActor.class)));

		this.context = this.system.dispatcher().prepare();
	}

	/**
	 * On Shutdown
	 */
	@Override
	public void stop() {
		this.system.shutdown();
	}

	/**
	 * Run a task inside a pool
	 * @param task task
	 */
	@Override
	public void runTask(Task task) {
		this.mainActor.tell(task, ActorRef.noSender());
	}

	/**
	 * Schedule a task inside a pool
	 * @param task task
	 * @param firstDelay first delay
	 * @param delayBetwwenTwoTasks delay between two tasks
	 */
	@Override
	public void scheduleTask(Task task, long firstDelay, long delayBetwwenTwoTasks) {
		this.system.scheduler().schedule(
			FiniteDuration.create(firstDelay, TimeUnit.MILLISECONDS),
			FiniteDuration.create(delayBetwwenTwoTasks, TimeUnit.MILLISECONDS),
			new AbstractFunction0<BoxedUnit>() {
				@Override
				public BoxedUnit apply() {
					PoolEngineImpl.this.mainActor.tell(task, ActorRef.noSender());
					return null;
				}
			}, this.context);
	}
}
