package com.orionemu.server.tasks;

import com.orionemu.server.config.Configuration;
import com.orionemu.server.game.rooms.types.components.ProcessComponent;
import com.orionemu.server.utilities.Initialisable;
import org.apache.log4j.Logger;

import java.util.concurrent.*;


public class OrionThreadManager implements Initialisable {
    private static OrionThreadManager orionThreadManagerInstance;

    public static int POOL_SIZE = 0;

    private ScheduledExecutorService coreExecutor;
    private ScheduledExecutorService roomProcessingExecutor;

    public OrionThreadManager() {

    }

    public static OrionThreadManager getInstance() {
        if (orionThreadManagerInstance == null)
            orionThreadManagerInstance = new OrionThreadManager();

        return orionThreadManagerInstance;
    }

    @Override
    public void initialize() {
        int poolSize = Integer.parseInt((String) Configuration.currentConfig().getOrDefault("orion.system.threads", "8"));

        this.coreExecutor = Executors.newScheduledThreadPool(poolSize, r -> {
            POOL_SIZE++;

            Thread scheduledThread = new Thread(r);
            scheduledThread.setName("Orion-Scheduler-Thread-" + POOL_SIZE);

            final Logger log = Logger.getLogger("Orion-Scheduler-Thread-" + POOL_SIZE);
            scheduledThread.setUncaughtExceptionHandler((t, e) -> log.error("Exception in worker thread", e));

            return scheduledThread;
        });

        final int roomProcessingPool = 8;

        this.roomProcessingExecutor = Executors.newScheduledThreadPool(roomProcessingPool, r -> {
            Thread scheduledThread = new Thread(r);
            scheduledThread.setName("Orion-Room-Scheduler-Thread-" + POOL_SIZE);

            final Logger log = Logger.getLogger("Orion-Room-Scheduler-Thread-" + POOL_SIZE);
            scheduledThread.setUncaughtExceptionHandler((t, e) -> log.error("Exception in room worker thread", e));

            return scheduledThread;
        });
    }

    public Future executeOnce(OrionTask task) {
        return this.coreExecutor.submit(task);
    }

    public Future executeOnceWithDelay(OrionTask task, int delay){
        return this.coreExecutor.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture executePeriodic(OrionTask task, long initialDelay, long period, TimeUnit unit) {
        if(task instanceof ProcessComponent) {
            // Handle room processing in a different pool, this should help against
            return this.roomProcessingExecutor.scheduleAtFixedRate(task, initialDelay, period, unit);
        }

        return this.coreExecutor.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    public ScheduledFuture executeSchedule(OrionTask task, long delay, TimeUnit unit) {
        if(task instanceof ProcessComponent) {
            return this.roomProcessingExecutor.schedule(task, delay, unit);
        }

        return this.coreExecutor.schedule(task, delay, unit);
    }

    public ScheduledExecutorService getCoreExecutor() {
        return coreExecutor;
    }
}