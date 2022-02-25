package com.orionemu.server.network.messages.outgoing.gamecenter.basejump;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;
/**
 * Created by Emrik on 2017-06-23.
 */
public class GameStatusButtonComposer extends MessageComposer {
    @Override
    public short getId() {
        return Composers.GameButtonStatusComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(3);
        msg.writeInt(0);

        msg.writeInt(0);
        msg.writeInt(0);
    }
}
