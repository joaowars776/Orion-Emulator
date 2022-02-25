package com.orionemu.server.network.messages.incoming.room.action;

import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.avatar.ShoutMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

public class ShoutMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
            String message = msg.readString();
            int colour = msg.readInt();

            if (!TalkMessageEvent.isValidColour(colour, client)) {
                colour = 0;
            }

            String filteredMessage = TalkMessageEvent.filterMessage(message);

            if (client.getPlayer().getEntity().onChat(filteredMessage)) {
                client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new ShoutMessageComposer(client.getPlayer().getEntity().getId(), filteredMessage, RoomManager.getInstance().getEmotions().getEmotion(filteredMessage), colour));
            }
        }
    }