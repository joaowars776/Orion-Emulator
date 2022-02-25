package com.orionemu.server.network.messages.incoming.room.filter;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

public class EditWordFilterMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {

    }
}
