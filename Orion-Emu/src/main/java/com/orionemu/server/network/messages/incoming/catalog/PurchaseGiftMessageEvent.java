package com.orionemu.server.network.messages.incoming.catalog;

import com.orionemu.server.game.catalog.CatalogManager;
import com.orionemu.server.game.catalog.types.CatalogOffer;
import com.orionemu.server.game.catalog.types.gifts.GiftData;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class PurchaseGiftMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int pageId = msg.readInt();
        int itemId = msg.readInt();

        if(pageId <= 0) {
            final CatalogOffer catalogOffer = CatalogManager.getCatalogOffers().get(itemId);

            if(catalogOffer == null) {
                return;
            }

            pageId = catalogOffer.getCatalogPageId();
            itemId = catalogOffer.getCatalogItemId();
        }

        String extraData = msg.readString();

        String sendingUser = msg.readString();
        String message = msg.readString();
        int spriteId = msg.readInt();
        int wrappingPaper = msg.readInt();
        int decorationType = msg.readInt();
        boolean showUsername = msg.readBoolean();

        if(!CatalogManager.getInstance().getGiftBoxesNew().contains(spriteId) && !CatalogManager.getInstance().getGiftBoxesOld().contains(spriteId)) {
            client.disconnect();
            return;
        }

        GiftData data = new GiftData(pageId, itemId, client.getPlayer().getId(), sendingUser, message, spriteId, wrappingPaper, decorationType, showUsername, extraData);

        CatalogManager.getInstance().getPurchaseHandler().purchaseItem(client, pageId, itemId, extraData, 1, data);
    }
}
