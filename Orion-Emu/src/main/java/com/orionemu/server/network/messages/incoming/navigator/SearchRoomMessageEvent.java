package com.orionemu.server.network.messages.incoming.navigator;

import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.navigator.NavigatorFlatListMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class SearchRoomMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        String query = msg.readString();
        client.send(new NavigatorFlatListMessageComposer(0, 8, "", RoomManager.getInstance().getRoomsByQuery(query)));
    }
}
