package com.orionemu.server.network.messages.incoming.room.moderation;

import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.settings.RoomPlayerUnbannedMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class RoomUnbanUserMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int playerId = msg.readInt();

        Room room = client.getPlayer().getEntity().getRoom();

        if (room == null || (room.getData().getOwnerId() != client.getPlayer().getId() && !client.getPlayer().getPermissions().getRank().roomFullControl())) {
            return;
        }

        if (room.getRights().hasBan(playerId)) {
            room.getRights().removeBan(playerId);
        }

        client.send(new RoomPlayerUnbannedMessageComposer(room.getId(), playerId));
    }
}
