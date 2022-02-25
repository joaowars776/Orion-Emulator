package com.orionemu.server.network.messages.incoming.performance;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.misc.PingMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class RequestLatencyTestMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.setLastPing(Orion.getTime());
        client.send(new PingMessageComposer());
    }
}
