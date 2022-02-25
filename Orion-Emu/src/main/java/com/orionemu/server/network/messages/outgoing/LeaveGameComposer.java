package com.orionemu.server.network.messages.outgoing;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

/**
 * Created by Emrik on 2017-06-27.
 */
public class LeaveGameComposer extends MessageComposer {
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(3);
    }

    @Override
    public short getId() {
        return Composers.LeaveGameComposer;
    }
}
