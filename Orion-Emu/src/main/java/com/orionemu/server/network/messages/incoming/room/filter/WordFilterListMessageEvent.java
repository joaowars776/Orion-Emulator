package com.orionemu.server.network.messages.incoming.room.filter;

import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

public class WordFilterListMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if (client.getPlayer().getEntity() == null) {
            return;
        }

        final Room room = client.getPlayer().getEntity().getRoom();

        if (room == null) {
            return;
        }
    }
}
