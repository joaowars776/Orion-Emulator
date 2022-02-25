//package com.orionproject.server.logging.database;
//
//import com.orionemu.server.boot.Orion;
//import com.jolbox.bonecp.BoneCP;
//import com.jolbox.bonecp.BoneCPConfig;
//import org.apache.log4j.Logger;
//
//
//public class LogDatabaseManager {
//    private static Logger log = Logger.getLogger(LogDatabaseHelper.class.getName());
//    private BoneCP connections = null;
//
//    public LogDatabaseManager() {
//        boolean isConnectionFailed = false;
//
//        try {
//            BoneCPConfig config = new BoneCPConfig();
//
//            config.setJdbcUrl("jdbc:mysql://" + Configuration.currentConfig().get("orion.game.logging.database.host") + "/" + Configuration.currentConfig().get("orion.game.logging.database.name"));
//            config.setUsername(Configuration.currentConfig().get("orion.game.logging.database.username"));
//            config.setPassword(Configuration.currentConfig().get("orion.game.logging.database.password"));
//
//            config.setMinConnectionsPerPartition(Integer.parseInt(Configuration.currentConfig().get("orion.db.pool.min")));
//            config.setMaxConnectionsPerPartition(Integer.parseInt(Configuration.currentConfig().get("orion.db.pool.max")));
//            config.setPartitionCount(Integer.parseInt(Configuration.currentConfig().get("orion.db.pool.count")));
//
//            this.connections = new BoneCP(config);
//        } catch (Exception e) {
//            isConnectionFailed = true;
//            log.error("Failed to connect to MySQL server", e);
//            // TODO: Disable logging...
//        } finally {
//            if (!isConnectionFailed) {
//                log.info("Connection to MySQL server was successful");
//            }
//        }
//    }
//
//    public BoneCP getConnections() {
//        return this.connections;
//    }
//}
