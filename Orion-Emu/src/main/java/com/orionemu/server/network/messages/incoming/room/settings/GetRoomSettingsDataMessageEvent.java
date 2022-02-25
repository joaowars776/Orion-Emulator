package com.orionemu.server.network.messages.incoming.room.settings;

import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.settings.RoomSettingsDataMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;


public class GetRoomSettingsDataMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int roomId = msg.readInt();

        Room room = RoomManager.getInstance().get(roomId);

        if (room == null) {
            return;
        }

        client.send(new RoomSettingsDataMessageComposer(room, client.getPlayer().getPermissions().getRank().modTool()));
    }
}
