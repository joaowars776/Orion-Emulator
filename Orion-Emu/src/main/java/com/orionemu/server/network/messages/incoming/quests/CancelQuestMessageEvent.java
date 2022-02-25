package com.orionemu.server.network.messages.incoming.quests;

import com.orionemu.server.game.quests.QuestManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.quests.QuestListMessageComposer;
import com.orionemu.server.network.messages.outgoing.quests.QuestStoppedMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

public class CancelQuestMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int questId = client.getPlayer().getData().getQuestId();

        if (questId != 0) {
            client.getPlayer().getQuests().cancelQuest(questId);
            client.getPlayer().getData().setQuestId(0);

            client.getPlayer().getData().save();
        }

        client.send(new QuestStoppedMessageComposer());
        client.send(new QuestListMessageComposer(QuestManager.getInstance().getQuests(), client.getPlayer(), false));
    }
}
