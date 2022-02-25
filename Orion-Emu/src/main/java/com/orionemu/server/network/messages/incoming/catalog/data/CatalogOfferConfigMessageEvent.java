package com.orionemu.server.network.messages.incoming.catalog.data;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.catalog.data.CatalogOfferConfigMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class CatalogOfferConfigMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        client.send(new CatalogOfferConfigMessageComposer());
    }
}
