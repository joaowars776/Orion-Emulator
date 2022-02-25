package com.orionemu.server.network.messages.incoming.catalog.data;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.catalog.data.CatalogOfferConfigMessageComposer;
import com.orionemu.server.network.messages.outgoing.catalog.data.GiftWrappingConfigurationMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class GetGiftWrappingConfigurationMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        client.send(new GiftWrappingConfigurationMessageComposer());
        client.send(new CatalogOfferConfigMessageComposer());
    }
}
