package com.orionemu.server.network.messages.incoming.room.trading;

import com.orionemu.server.game.rooms.types.components.types.Trade;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class UnacceptTradeMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if (client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) return;

        Trade trade = client.getPlayer().getEntity().getRoom().getTrade().get(client.getPlayer().getEntity());

        if (trade == null) return;

        trade.unaccept(trade.getUserNumber(client.getPlayer().getEntity()));
    }
}
