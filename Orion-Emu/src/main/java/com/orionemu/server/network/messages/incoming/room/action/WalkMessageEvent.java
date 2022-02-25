package com.orionemu.server.network.messages.incoming.room.action;

import com.orionemu.server.game.rooms.objects.entities.pathfinding.Square;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

import java.util.ArrayList;
import java.util.List;


public class WalkMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int goalX = msg.readInt();
        int goalY = msg.readInt();

        try {
            if (client.getPlayer().getEntity() == null) {
                return;
            }

            if (goalX == client.getPlayer().getEntity().getPosition().getX() && goalY == client.getPlayer().getEntity().getPosition().getY()) {
                return;
            }

            if (client.getPlayer().getEntity().hasAttribute("teleport")) {
                client.getPlayer().getData().setOHF(false);
                List<Square> squares = new ArrayList<>();
                squares.add(new Square(goalX, goalY));

                client.getPlayer().getEntity().setWalkingPath(squares);
                client.getPlayer().getEntity().setWalkingGoal(goalX, goalY);
                return;
            }

            if (!client.getPlayer().getEntity().isOverriden()) {
                if (client.getPlayer().getData().isOHF()) {
                    client.getPlayer().getEntity().setOHFWalking(true);
                    client.getPlayer().getEntity().moveToOHF(goalX, goalY);
                } else {
                    client.getPlayer().getEntity().moveTo(goalX, goalY);
                }
            }
        } catch (Exception e) {
            client.getLogger().error("Error while finding path", e);
        }
    }
}
