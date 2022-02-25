package com.orionemu.server.network.messages.outgoing.quests;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.players.types.Player;
import com.orionemu.server.game.quests.types.Quest;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class QuestStartedMessageComposer extends MessageComposer {
    private final Player player;
    private final Quest quest;

    public QuestStartedMessageComposer(Quest quest, Player player) {
        this.quest = quest;
        this.player = player;
    }

    @Override
    public short getId() {
        return Composers.QuestStartedMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        this.quest.compose(player, msg);
    }
}
