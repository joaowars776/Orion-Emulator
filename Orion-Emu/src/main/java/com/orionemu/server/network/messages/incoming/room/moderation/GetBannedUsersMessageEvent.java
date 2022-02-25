package com.orionemu.server.network.messages.incoming.room.moderation;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.settings.RoomBannedListMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class GetBannedUsersMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        if (client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) return;

        Room room = client.getPlayer().getEntity().getRoom();

        if ((room.getData().getOwnerId() != client.getPlayer().getId() && !client.getPlayer().getPermissions().getRank().roomFullControl())) {
            return;
        }

        if(Orion.getTime() - client.getPlayer().lastBannedListRequest < 5) {
            return;
        }

        client.getPlayer().lastBannedListRequest = (int) Orion.getTime();

        client.send(new RoomBannedListMessageComposer(room.getId(), room.getRights().getBannedPlayers()));
    }
}
