package com.orionemu.server.network.messages.incoming.navigator;

import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.navigator.CreateRoomMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class CreateRoomMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        String name = msg.readString();
        String model = msg.readString();

        if (RoomManager.getInstance().getModel(model) == null) {
            client.getPlayer().sendNotif("Invalid room model");
            return;
        }

        int roomId = RoomManager.getInstance().createRoom(name, model, client);

        client.send(new CreateRoomMessageComposer(roomId, name));
    }
}
