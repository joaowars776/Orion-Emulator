package com.orionemu.server.network.messages.incoming.catalog;

import com.orionemu.server.game.catalog.CatalogManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class PurchaseItemMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int pageId = msg.readInt();
        int itemId = msg.readInt();
        String data = msg.readString();
        int amount = msg.readInt();

        CatalogManager.getInstance().getPurchaseHandler().purchaseItem(client, pageId, itemId, data, amount, null);
    }
}
