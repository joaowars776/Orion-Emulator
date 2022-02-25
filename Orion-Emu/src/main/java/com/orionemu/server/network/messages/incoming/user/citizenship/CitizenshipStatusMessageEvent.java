package com.orionemu.server.network.messages.incoming.user.citizenship;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class CitizenshipStatusMessageEvent implements Event {

    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
//        client.send(new CitizenshipStatusMessageComposer());
    }
}
