package com.orionemu.server.network.messages.incoming.room.settings;

import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.settings.RightsListMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class UsersWithRightsMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int roomId = msg.readInt();

        Room room = client.getPlayer().getEntity().getRoom();

        if (room == null) {
            return;
        }

        client.send(new RightsListMessageComposer(room.getId(), room.getRights().getAll()));
    }
}
