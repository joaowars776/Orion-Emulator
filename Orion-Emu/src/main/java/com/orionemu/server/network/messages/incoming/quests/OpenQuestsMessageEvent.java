package com.orionemu.server.network.messages.incoming.quests;

import com.orionemu.server.game.quests.QuestManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.quests.QuestListMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class OpenQuestsMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.send(new QuestListMessageComposer(QuestManager.getInstance().getQuests(), client.getPlayer(), true));
    }
}
