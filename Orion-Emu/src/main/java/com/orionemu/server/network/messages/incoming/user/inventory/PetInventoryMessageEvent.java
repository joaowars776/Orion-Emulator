package com.orionemu.server.network.messages.incoming.user.inventory;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.user.inventory.PetInventoryMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class PetInventoryMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.send(new PetInventoryMessageComposer(client.getPlayer().getPets().getPets()));
    }
}
