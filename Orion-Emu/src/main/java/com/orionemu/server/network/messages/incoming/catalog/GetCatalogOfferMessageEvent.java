package com.orionemu.server.network.messages.incoming.catalog;

import com.orionemu.server.game.catalog.CatalogManager;
import com.orionemu.server.game.catalog.types.CatalogItem;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.catalog.CatalogOfferMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class GetCatalogOfferMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int offerId = msg.readInt();

        if (offerId == -1)
            return;

        CatalogItem catalogItem = CatalogManager.getInstance().getCatalogItemByOfferId(offerId);

        if (catalogItem != null) {
            client.send(new CatalogOfferMessageComposer(catalogItem));
        }
    }
}
