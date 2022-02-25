package com.orionemu.server.network.messages.incoming;

import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public interface Event {
    public void handle(Session client, MessageEvent msg) throws Exception;
}
