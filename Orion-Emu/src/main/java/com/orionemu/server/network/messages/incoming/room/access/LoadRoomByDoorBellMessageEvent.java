package com.orionemu.server.network.messages.incoming.room.access;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class LoadRoomByDoorBellMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if (client.getPlayer().getEntity() == null) {
            return;
        }

        // re-do room checks like max player check etc
        client.getPlayer().getEntity().joinRoom(client.getPlayer().getEntity().getRoom(), "");
    }
}
