package com.orionemu.server.network.messages.outgoing.gamecenter;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class GameCenterAchievementsConfigurationComposer extends MessageComposer {
    private int gameId;

    public GameCenterAchievementsConfigurationComposer(int gameId){
        this.gameId = gameId;
    }
    @Override
    public short getId() {
        return Composers.GameCenterAchievementsConfigurationComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(gameId);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(3);
        msg.writeInt(1);
        msg.writeInt(1);
        msg.writeString("basejump");
        msg.writeInt(1);
    }
}