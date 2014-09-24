package engines.pool;

import engines.Engine;

/**
 * A pool engine that allows to run tasks in a pool
 * @author Jerome Baudoux
 */
public interface PoolEngine extends Engine {

	/**
	 * Run a task inside a pool
	 * @param task task
	 */
	void runTask(Task task);
	
	/**
	 * Schedule a task inside a pool
	 * @param task task
	 * @param firstDelay first delay
	 * @param delayBetwwenTwoTasks delay between two tasks
	 */
	void scheduleTask(Task task, long firstDelay, long delayBetwwenTwoTasks);
	
	/**
	 * A task to run
	 * @author Jerome Baudoux
	 */
	public interface Task {
		void run();
	}
}
