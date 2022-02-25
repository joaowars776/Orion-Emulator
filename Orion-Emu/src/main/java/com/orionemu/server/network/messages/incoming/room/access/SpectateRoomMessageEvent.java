package com.orionemu.server.network.messages.incoming.room.access;

import com.orionemu.server.game.rooms.RoomQueue;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.engine.HotelViewMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.engine.RoomForwardMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

public class SpectateRoomMessageEvent implements Event {

    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if(client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) {
            return;
        }

        int roomId = client.getPlayer().getEntity().getRoom().getId();

        if(!RoomQueue.getInstance().hasQueue(roomId)) {
            client.send(new HotelViewMessageComposer());
            return;
        }

        // enter the room normally but then make sure they are invisible & are spectating.
        client.getPlayer().setSpectatorRoomId(roomId);

        RoomQueue.getInstance().removePlayerFromQueue(roomId, client.getPlayer().getId());
        client.send(new RoomForwardMessageComposer(roomId));
    }
}
