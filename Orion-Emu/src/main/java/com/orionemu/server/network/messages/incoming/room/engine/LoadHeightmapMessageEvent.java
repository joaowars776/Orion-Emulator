package com.orionemu.server.network.messages.incoming.room.engine;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.engine.HeightmapMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class LoadHeightmapMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        if (client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom().getModel() == null) {
            return;
        }

        client.sendQueue(new HeightmapMessageComposer(client.getPlayer().getEntity().getRoom().getModel()));
        client.sendQueue(client.getPlayer().getEntity().getRoom().getModel().getRelativeHeightmapMessage());
        client.flush();
    }
}
