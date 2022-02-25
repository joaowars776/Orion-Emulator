package com.orionemu.server.network.messages.outgoing.gamecenter;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class PlayableGamesComposer extends MessageComposer {
    private int gameID;

    public PlayableGamesComposer(int gameID){
        this.gameID = gameID;
    }

    @Override
    public short getId() {
        return Composers.PlayableGamesMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(gameID);
        msg.writeInt(0);
    }
}