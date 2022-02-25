package com.orionemu.server.network.messages.incoming.handshake;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class UniqueIdMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        String deviceId = msg.readString();
        String fingerprint = msg.readString();

//        if(deviceId == null)
//            deviceId = fingerprint;

        client.setUniqueId(fingerprint);
    }
}
