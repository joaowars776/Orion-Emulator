package com.orionemu.server.network.messages.incoming.catalog;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.catalog.CatalogIndexMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class GetCataIndexMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
//        if (msg.readString().equals("BUILDERS_CLUB")) {
//            client.getPlayer().cancelPageOpen = true;
//            return;
//        }

        client.send(new CatalogIndexMessageComposer(client.getPlayer().getData().getRank()));
    }
}
