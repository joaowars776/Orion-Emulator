package com.orionemu.server.network.messages.incoming.navigator;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.navigator.NavigatorFlatListMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.rooms.RoomDao;


public class NavigatorGetHighRatedRoomsMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.send(new NavigatorFlatListMessageComposer(0, 0, "", RoomDao.getHighestScoredRooms(), true));
    }
}
