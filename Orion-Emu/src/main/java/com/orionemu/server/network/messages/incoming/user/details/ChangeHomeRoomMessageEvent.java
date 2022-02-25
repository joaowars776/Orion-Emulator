package com.orionemu.server.network.messages.incoming.user.details;

import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.handshake.HomeRoomMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.PlayerDao;


public class ChangeHomeRoomMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if(client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) {
            return;
        }

        Room room = client.getPlayer().getEntity().getRoom();

        int roomId = room.getId();

        client.send(new HomeRoomMessageComposer(client.getPlayer().getSettings().getHomeRoom(), roomId));
        client.getPlayer().getSettings().setHomeRoom(roomId);

        PlayerDao.updateHomeRoom(roomId, client.getPlayer().getId());
    }
}
