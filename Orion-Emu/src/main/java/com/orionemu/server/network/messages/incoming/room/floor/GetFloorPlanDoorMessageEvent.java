package com.orionemu.server.network.messages.incoming.room.floor;

import com.orionemu.server.game.rooms.models.RoomModel;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.floor.FloorPlanDoorMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class GetFloorPlanDoorMessageEvent implements Event {

    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if (client.getPlayer().getEntity() != null) {
            RoomModel model = client.getPlayer().getEntity().getRoom().getModel();

            if (model == null) return;

            client.send(new FloorPlanDoorMessageComposer(model.getDoorX(), model.getDoorY(), model.getDoorRotation()));
        }
    }
}
