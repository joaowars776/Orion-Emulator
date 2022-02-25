package com.orionemu.server.network.messages.incoming.room.action;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.engine.HotelViewMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class ExitRoomMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        if (client.getPlayer() == null || client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) {
            client.send(new HotelViewMessageComposer());
            return;
        }

        client.getPlayer().getEntity().leaveRoom(false, false, true);
    }
}
