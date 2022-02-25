package com.orionemu.server.network.messages.incoming.user.inventory;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.user.inventory.SongInventoryMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

public class SongInventoryMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if (!client.getPlayer().getInventory().itemsLoaded()) {
            client.getPlayer().getInventory().loadItems();
        }

        client.send(new SongInventoryMessageComposer(client.getPlayer().getInventory().getSongs()));
    }
}
