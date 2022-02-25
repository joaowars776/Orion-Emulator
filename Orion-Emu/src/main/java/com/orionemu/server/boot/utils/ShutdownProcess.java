package com.orionemu.server.boot.utils;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.storage.queue.types.ItemStorageQueue;
import com.orionemu.server.storage.queue.types.PlayerDataStorageQueue;
import com.orionemu.server.logging.LogManager;
import com.orionemu.server.logging.database.queries.LogQueries;
import com.orionemu.server.storage.StorageManager;
import com.orionemu.server.storage.queries.system.StatisticsDao;
import org.apache.log4j.Logger;


public class ShutdownProcess {
    private static final Logger log = Logger.getLogger(ShutdownProcess.class.getName());

    public static void init() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                shutdown(false);
            }
        });
    }

    public static void shutdown(boolean exit) {
        log.info("Orion is now shutting down");

        Orion.isRunning = false;

        PlayerDataStorageQueue.getInstance().shutdown();
        ItemStorageQueue.getInstance().shutdown();

        log.info("Resetting statistics");
        StatisticsDao.saveStatistics(0, 0, Orion.getBuild());

        if (LogManager.ENABLED) {
            log.info("Updating room entry data");
            LogQueries.updateRoomEntries();
        }

        log.info("Closing all database connections");
        StorageManager.getInstance().getConnections().shutdown();

        if(exit) {
            System.exit(0);
        }
    }
}
