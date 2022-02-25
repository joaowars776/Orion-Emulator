package com.orionemu.server.network.messages.incoming.navigator;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.navigator.CanCreateRoomMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class CanCreateRoomMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        client.send(new CanCreateRoomMessageComposer());
    }
}
