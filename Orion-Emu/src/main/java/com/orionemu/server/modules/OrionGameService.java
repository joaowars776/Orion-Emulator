package com.orionemu.server.modules;

import com.orionemu.api.events.EventHandler;
import com.orionemu.api.networking.sessions.ISessionManager;
import com.orionemu.api.server.IGameService;
import com.orionemu.server.network.NetworkManager;

public class OrionGameService implements IGameService {
    /**
     * The main system-wide event handler
     */
    private EventHandler eventHandler;

    /**
     * Default constructor
     */
    public OrionGameService(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * Get the main event handler
     *
     * @return EventHandler instance
     */
    @Override
    public EventHandler getEventHandler() {
        return this.eventHandler;
    }

    @Override
    public ISessionManager getSessionManager() {
        return NetworkManager.getInstance().getSessions();
    }
}
