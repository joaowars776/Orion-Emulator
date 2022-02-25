package com.orionemu.server.network.messages.incoming.navigator;

import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.types.RoomData;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.navigator.NavigatorFlatListMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

import java.util.LinkedList;
import java.util.List;


public class OwnRoomsMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        List<RoomData> rooms = new LinkedList<>();

        for (Integer roomId : client.getPlayer().getRooms()) {
            rooms.add(RoomManager.getInstance().getRoomData(roomId));
        }

        client.send(new NavigatorFlatListMessageComposer( 0, 5, "", rooms,false));
    }
}
