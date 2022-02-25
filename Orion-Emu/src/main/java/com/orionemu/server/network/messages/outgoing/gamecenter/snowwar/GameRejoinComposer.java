package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class GameRejoinComposer extends MessageComposer {

    private final int roomId;

    public GameRejoinComposer(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(roomId);
    }

    @Override
    public short getId() {
        return 0;
    }
}
