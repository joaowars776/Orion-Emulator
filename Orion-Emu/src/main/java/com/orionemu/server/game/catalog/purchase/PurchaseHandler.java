package com.orionemu.server.game.catalog.purchase;

import com.orionemu.server.game.catalog.types.CatalogItem;
import com.orionemu.server.network.sessions.Session;

public interface PurchaseHandler {
    PurchaseResult handlePurchaseData(Session session, String purchaseData, CatalogItem catalogItem, int amount);
}
