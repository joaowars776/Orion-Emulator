package com.orionemu.server.network.messages.incoming.navigator;

import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.navigator.NavigatorFlatListMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class PopularRoomsMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int categoryId = Integer.parseInt(msg.readString());

    client.send(new NavigatorFlatListMessageComposer(0, 2, "", RoomManager.getInstance().getRoomsByCategory(categoryId)));
    }
}
