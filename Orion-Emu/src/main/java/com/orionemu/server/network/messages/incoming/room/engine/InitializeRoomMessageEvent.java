package com.orionemu.server.network.messages.incoming.room.engine;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class InitializeRoomMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int id = msg.readInt();
        String password = msg.readString();


        client.getPlayer().loadRoom(id, password);
    }
}
