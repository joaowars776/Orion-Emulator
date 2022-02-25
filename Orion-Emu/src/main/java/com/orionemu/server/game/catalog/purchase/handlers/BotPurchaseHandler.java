package com.orionemu.server.game.catalog.purchase.handlers;

import com.orionemu.server.game.catalog.purchase.PurchaseHandler;
import com.orionemu.server.game.catalog.purchase.PurchaseResult;
import com.orionemu.server.game.catalog.types.CatalogItem;
import com.orionemu.server.network.sessions.Session;

public class BotPurchaseHandler implements PurchaseHandler {
    @Override
    public PurchaseResult handlePurchaseData(Session session, String purchaseData, CatalogItem catalogItem, int amount) {
        return null;
    }
}
