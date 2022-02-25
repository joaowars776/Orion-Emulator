package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class StageRunningComposer extends MessageComposer {

    private final int seconds;

    public StageRunningComposer(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public void compose(IComposer msg) {
		msg.writeInt(seconds);
    }

    @Override
    public short getId() {
        return 1244;
    }
}
