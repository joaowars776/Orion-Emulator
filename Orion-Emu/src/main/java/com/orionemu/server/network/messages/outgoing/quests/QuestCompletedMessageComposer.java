package com.orionemu.server.network.messages.outgoing.quests;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.players.types.Player;
import com.orionemu.server.game.quests.types.Quest;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class QuestCompletedMessageComposer extends MessageComposer {

    private Quest quest;
    private Player player;

    public QuestCompletedMessageComposer(Quest quest, Player player) {
        this.quest = quest;
        this.player = player;
    }

    @Override
    public short getId() {
        return Composers.QuestCompletedMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        quest.compose(this.player, msg);
        msg.writeBoolean(this.player.getQuests().hasCompletedQuest(this.quest.getId()));
    }
}
