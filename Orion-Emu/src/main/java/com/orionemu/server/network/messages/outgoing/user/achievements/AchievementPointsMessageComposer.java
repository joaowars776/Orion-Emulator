package com.orionemu.server.network.messages.outgoing.user.achievements;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class AchievementPointsMessageComposer extends MessageComposer {
    private final int points;

    public AchievementPointsMessageComposer(final int points) {
        this.points = points;
    }

    @Override
    public short getId() {
        return Composers.AchievementScoreMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.points);
    }
}
