package com.orionemu.server.network.messages.incoming.room.settings;

import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class RateRoomMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if (client.getPlayer().getEntity() == null) {
            return;
        }

        Room room = client.getPlayer().getEntity().getRoom();

        if (!client.getPlayer().getEntity().canRateRoom()) {
            return;
        }

        room.getRatings().add(client.getPlayer().getId());

        int direction = msg.readInt();
        int score = room.getData().getScore();

        if (direction == 1)
            score++;
        else
            score--;

        room.getData().setScore(score);
        room.getEntities().refreshScore();

        room.getData().save();
    }
}
