package com.orionemu.server.game.catalog.purchase.handlers;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.catalog.purchase.PurchaseHandler;
import com.orionemu.server.game.catalog.purchase.PurchaseResult;
import com.orionemu.server.game.catalog.types.CatalogItem;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.filter.FilterResult;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.sessions.Session;
import org.joda.time.DateTime;

public class TrophyPurchaseHandler implements PurchaseHandler {
    @Override
    public PurchaseResult handlePurchaseData(Session session, String purchaseData, CatalogItem catalogItem, int amount) {

        if (!session.getPlayer().getPermissions().getRank().roomFilterBypass()) {
            FilterResult filterResult = RoomManager.getInstance().getFilter().filter(purchaseData);

            if (filterResult.isBlocked()) {
                session.send(new AdvancedAlertMessageComposer(Locale.get("game.message.blocked").replace("%s", filterResult.getMessage())));
                return null;
            } else if (filterResult.wasModified()) {
                purchaseData = filterResult.getMessage();
            }
        }

        return new PurchaseResult(1, session.getPlayer().getData().getUsername()
                + Character.toChars(9)[0]
                + DateTime.now().getDayOfMonth() + "-" + DateTime.now().getMonthOfYear() + "-" + DateTime.now().getYear() +
                Character.toChars(9)[0] + purchaseData);
    }
}
