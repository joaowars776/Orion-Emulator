package com.orionemu.server.network.messages.incoming.quests;

import com.orionemu.server.game.quests.types.Quest;
import com.orionemu.server.game.quests.QuestManager;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

public class StartQuestMessageEvent implements com.orionemu.server.network.messages.incoming.Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int questId = msg.readInt();

        if (client.getPlayer().getQuests().hasStartedQuest(questId)) {
            // Already started it!
            return;
        }

        if (client.getPlayer().getData().getQuestId() != 0) {
            // We need to cancel their current one.
            if (!client.getPlayer().getQuests().hasCompletedQuest(client.getPlayer().getData().getQuestId())) {
                client.getPlayer().getQuests().cancelQuest(client.getPlayer().getData().getQuestId());
            }
        }

        final Quest quest = QuestManager.getInstance().getById(questId);

        if (quest == null) {
            return;
        }

        client.getPlayer().getQuests().startQuest(quest);
    }
}
