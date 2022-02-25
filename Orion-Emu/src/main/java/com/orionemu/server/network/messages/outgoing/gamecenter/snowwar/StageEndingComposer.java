package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class StageEndingComposer extends MessageComposer {
    @Override
    public void compose(IComposer msg) {
		msg.writeInt(0);
    }

    @Override
    public short getId() {
        return 1907;
    }
}
