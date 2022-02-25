package com.orionemu.server.network.messages.incoming.room.action;

import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class LookToMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        PlayerEntity avatar = client.getPlayer().getEntity();

        if (avatar == null) {
            return;
        }

        if(!client.getPlayer().getEntity().isVisible()) {
            return;
        }

        int x = msg.readInt();
        int y = msg.readInt();

        if(avatar.getMountedEntity() != null) {
            return;
        }

        avatar.lookTo(x, y);
    }
}
