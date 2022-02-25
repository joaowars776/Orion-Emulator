package com.orionemu.server.storage;

import com.orionemu.server.config.Configuration;
import com.orionemu.server.storage.cache.CacheManager;
import com.orionemu.server.utilities.Initialisable;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;

public class StorageManager implements Initialisable {
    private static StorageManager storageManagerInstance;
    private static Logger log = Logger.getLogger(StorageManager.class.getName());
    private HikariDataSource connections = null;

    public StorageManager() {

    }

    @Override
    public void initialize() {
        boolean isConnectionFailed = false;

        try {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(
                    "jdbc:mysql://" + Configuration.currentConfig().get("orion.db.host") +
                            "/" + Configuration.currentConfig().get("orion.db.name") + "?tcpKeepAlive=" + Configuration.currentConfig().get("orion.db.pool.tcpKeepAlive") +
                            "&autoReconnect=" + Configuration.currentConfig().get("orion.db.pool.autoReconnect")
            );

            config.setUsername(Configuration.currentConfig().get("orion.db.username"));
            config.setPassword(Configuration.currentConfig().get("orion.db.password"));

            config.setMaximumPoolSize(Integer.parseInt(Configuration.currentConfig().get("orion.db.pool.max")));

//            config.setMaxConnectionsPerPartition(Integer.parseInt(Configuration.currentConfig().get("orion.db.pool.max")));
//            config.setPartitionCount(Integer.parseInt(Configuration.currentConfig().get("orion.db.pool.count")));
//
//            config.setIdleMaxAge(Integer.valueOf(Configuration.currentConfig().get("orion.db.pool.idleMaxAgeSeconds")), TimeUnit.SECONDS);
//            config.setMaxConnectionAge(Integer.valueOf(Configuration.currentConfig().get("orion.db.pool.maxConnectionAgeSeconds")), TimeUnit.SECONDS);
//
//            config.setAcquireRetryAttempts(Integer.valueOf(Configuration.currentConfig().get("orion.db.pool.acquireRetryAttempts")));

            log.info("Connecting to the MySQL server");

            this.connections = new HikariDataSource(config);
        } catch (Exception e) {
            isConnectionFailed = true;
            log.error("Failed to connect to MySQL server", e);
            System.exit(0);
        } finally {
            if (!isConnectionFailed) {
                log.info("Connection to MySQL server was successful");
            }
        }

        CacheManager.getInstance().initialize();
        SqlHelper.init(this);
    }

    public static StorageManager getInstance() {
        if (storageManagerInstance == null)
            storageManagerInstance = new StorageManager();

        return storageManagerInstance;
    }

    public HikariDataSource getConnections() {
        return this.connections;
    }
}
