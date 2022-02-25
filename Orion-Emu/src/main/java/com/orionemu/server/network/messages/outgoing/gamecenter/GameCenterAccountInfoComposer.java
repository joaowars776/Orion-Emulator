package com.orionemu.server.network.messages.outgoing.gamecenter;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class GameCenterAccountInfoComposer extends MessageComposer {
    int gameId;

    public GameCenterAccountInfoComposer(int gameId){
        this.gameId = gameId;
    }
    @Override
    public short getId() {
        return Composers.GameCenterAccountInfoComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(gameId);
        msg.writeInt(-1);
        msg.writeInt(0);
    }
}