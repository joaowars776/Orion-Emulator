package com.orionemu.server.network.messages.incoming.user.inventory;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.user.inventory.BotInventoryMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class BotInventoryMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        client.send(new BotInventoryMessageComposer(client.getPlayer().getBots().getBots()));
    }
}
