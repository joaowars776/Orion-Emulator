package com.orionemu.server.network.messages.incoming.room.action;

import com.orionemu.server.game.quests.types.QuestType;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.avatar.DanceMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class ApplyDanceMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int danceId = msg.readInt();

        if (client.getPlayer().getEntity().getDanceId() == danceId) {
            return;
        }

        client.getPlayer().getEntity().unIdle();

        if(!client.getPlayer().getEntity().isVisible()) {
            return;
        }

        client.getPlayer().getEntity().setDanceId(danceId);
        client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new DanceMessageComposer(client.getPlayer().getEntity().getId(), danceId));

        client.getPlayer().getQuests().progressQuest(QuestType.SOCIAL_DANCE);
    }
}
