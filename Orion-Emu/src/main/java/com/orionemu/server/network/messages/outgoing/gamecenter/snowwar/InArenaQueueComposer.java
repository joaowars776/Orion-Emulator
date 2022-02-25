package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class InArenaQueueComposer extends MessageComposer {

    private final int position;

    public InArenaQueueComposer(int position) {
        this.position = position;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.position);
    }

    @Override
    public short getId() {
        return 1915;
    }
}
