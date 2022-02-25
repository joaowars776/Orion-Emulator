package com.orionemu.api.server;

import com.orionemu.api.events.EventHandler;
import com.orionemu.api.networking.sessions.ISessionManager;

public interface IGameService {
    ISessionManager getSessionManager();

    EventHandler getEventHandler();
}
