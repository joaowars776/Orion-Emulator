package com.orionemu.server.network.messages.incoming.room.trading;

import com.orionemu.api.game.rooms.settings.RoomTradeState;
import com.orionemu.server.game.rooms.objects.entities.RoomEntityStatus;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.types.components.types.Trade;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.trading.TradeErrorMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class BeginTradeMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        if (client.getPlayer().getEntity() == null)
            return;

        int userId = msg.readInt();

        if (client.getPlayer().getEntity().getRoom().getData().getTradeState() == RoomTradeState.DISABLED) {
            System.out.println("denna1");
            return;
        }

        PlayerEntity entity = (PlayerEntity) client.getPlayer().getEntity().getRoom().getEntities().getEntity(userId);

        if (client.getPlayer().getEntity().getRoom().getData().getOwnerId() != client.getPlayer().getId() && entity.getRoom().getData().getTradeState() == RoomTradeState.OWNER_ONLY) {

            System.out.println("denna2");
            return;
        }

        if(entity.getPlayer().getSettings() != null && !entity.getPlayer().getSettings().getAllowTrade()) {
            System.out.println("denna3");
            return;
        }

        if (client == null || client.getPlayer().getEntity().hasStatus(RoomEntityStatus.TRADE) || client.getPlayer() == null || client.getPlayer().getSession() == null) {
            System.out.println("denna4");
            return;
        }

        if (entity == null || entity.hasStatus(RoomEntityStatus.TRADE) || entity.getPlayer() == null || entity.getPlayer().getSession() == null) {
            System.out.println("denna5");
            return;
        }

        long currentTime = System.currentTimeMillis();

        if(client.getPlayer().getLastTradeFlood() != 0) {
            long timeFloodEnds = client.getPlayer().getLastTradeTime() + ((client.getPlayer().getLastTradeFlag() * 1000));

            if(currentTime >= timeFloodEnds) {
                client.getPlayer().setLastTradeFlood(0);
            } else {
                return;
            }
        }

        if((currentTime - client.getPlayer().getLastTradeTime()) < 750) {
            client.getPlayer().setLastTradeTime(currentTime);

            if(client.getPlayer().getLastTradeFlag() >= 3) {
                client.getPlayer().setLastTradeFlood(30);
                return;
            }

            client.getPlayer().setLastTradeFlag(client.getPlayer().getLastTradeFlag() + 1);
        }

        client.getPlayer().getEntity().getRoom().getTrade().add(new Trade(client.getPlayer().getEntity(), entity));
    }
}
