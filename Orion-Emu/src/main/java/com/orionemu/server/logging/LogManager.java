package com.orionemu.server.logging;

import com.orionemu.server.config.Configuration;
import com.orionemu.server.utilities.Initialisable;
import org.apache.log4j.Logger;


public class LogManager implements Initialisable {
    private static LogManager logManagerInstance;

    private Logger log = Logger.getLogger(LogManager.class.getName());

    public static final boolean ENABLED = Configuration.currentConfig().get("orion.game.logging.enabled").equals("true");

    private LogStore store;

    public LogManager() {
    }

    @Override
    public void initialize() {
        this.store = new LogStore();
    }

    public static LogManager getInstance() {
        if (logManagerInstance == null) {
            logManagerInstance = new LogManager();
        }

        return logManagerInstance;
    }


    public LogStore getStore() {
        return store;
    }
}