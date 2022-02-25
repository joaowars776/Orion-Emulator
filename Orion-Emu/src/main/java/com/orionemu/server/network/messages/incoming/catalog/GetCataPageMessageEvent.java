package com.orionemu.server.network.messages.incoming.catalog;

import com.orionemu.server.game.catalog.CatalogManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.catalog.CatalogPageMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class GetCataPageMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int pageId = msg.readInt();
        msg.readInt();
        String type = msg.readString();

        if (CatalogManager.getInstance().pageExists(pageId) && CatalogManager.getInstance().getPage(pageId).isEnabled()) {
            client.send(new CatalogPageMessageComposer(CatalogManager.getInstance().getPage(pageId), type));
        }
    }
}
