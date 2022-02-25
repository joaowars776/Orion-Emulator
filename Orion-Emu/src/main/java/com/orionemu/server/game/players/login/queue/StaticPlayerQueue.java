package com.orionemu.server.game.players.login.queue;

import com.orionemu.server.tasks.OrionThreadManager;


public class StaticPlayerQueue {
    private static PlayerLoginQueueManager mgr;

    static {

    }

    public static void init(OrionThreadManager threadManagement) {
        mgr = new PlayerLoginQueueManager(true, threadManagement);
    }

    public static PlayerLoginQueueManager getQueueManager() {
        return mgr;
    }
}
