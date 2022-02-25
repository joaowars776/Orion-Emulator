package com.orionemu.server.network.messages.incoming.room.action;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.avatar.TypingStatusMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class StartTypingMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        if (client.getPlayer() == null || client.getPlayer().getEntity() == null)
            return;

        if(!client.getPlayer().getEntity().isVisible()) {
            return;
        }

        client.getPlayer().getEntity().unIdle();
        client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new TypingStatusMessageComposer(client.getPlayer().getEntity().getId(), 1));
    }
}
