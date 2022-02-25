package com.orionemu.server.network.messages.outgoing.gamecenter.basejump;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class BaseJumpJoinQueueComposer extends MessageComposer {
    private int gameID;

    public BaseJumpJoinQueueComposer(int gameID){
        this.gameID = gameID;
    }
    @Override
    public short getId() {
        return Composers.BaseJumpJoinQueueComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(gameID);
    }
}