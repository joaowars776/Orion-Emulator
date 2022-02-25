package com.orionemu.server.network.messages.incoming.navigator;

import com.orionemu.server.game.navigator.NavigatorManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.navigator.FeaturedRoomsMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class FeaturedRoomsMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) {
        client.send(new FeaturedRoomsMessageComposer(NavigatorManager.getInstance().getFeaturedRooms()));
    }
}
