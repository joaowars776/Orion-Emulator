package com.orionemu.server.network.messages.incoming.room.moderation;

import com.orionemu.api.game.rooms.settings.RoomMuteState;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

public class MutePlayerMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int playerId = msg.readInt();
        int unk = msg.readInt();
        int lengthMinutes = msg.readInt();

        if (client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) {
            return;
        }

        final Room room = client.getPlayer().getEntity().getRoom();

        if (client.getPlayer().getId() != room.getData().getOwnerId() && room.getData().getMuteState() != RoomMuteState.RIGHTS && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            return;
        }

        if (playerId == room.getData().getOwnerId()) {
            return;
        }

        room.getRights().addMute(playerId, lengthMinutes);
    }
}
