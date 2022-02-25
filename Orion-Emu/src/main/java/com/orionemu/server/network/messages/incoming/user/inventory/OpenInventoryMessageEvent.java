package com.orionemu.server.network.messages.incoming.user.inventory;

import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.user.inventory.InventoryMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class OpenInventoryMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        PlayerManager.getInstance().getPlayerLoadExecutionService().submit(() -> {
            if (!client.getPlayer().getInventory().itemsLoaded()) {
                client.getPlayer().getInventory().loadItems();
            }

            client.send(new InventoryMessageComposer(client.getPlayer().getInventory()));
        });
    }
}
