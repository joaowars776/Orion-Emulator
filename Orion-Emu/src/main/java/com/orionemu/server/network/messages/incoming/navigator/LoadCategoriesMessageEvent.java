package com.orionemu.server.network.messages.incoming.navigator;

import com.orionemu.server.game.navigator.NavigatorManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.navigator.RoomCategoriesMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;


public class LoadCategoriesMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        //client.send(new RoomCategoriesMessageComposer(NavigatorManager.getInstance().getUserCategories(), client.getPlayer().getData().getRank()));
//        client.send(new EventCategoriesMessageComposer());
    }
}
