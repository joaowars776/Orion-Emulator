package com.orionemu.server.network.messages.outgoing.room.access;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class DoorbellAcceptedComposer extends MessageComposer {
    @Override
    public short getId() {
        return Composers.FlatAccessibleMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeString("");
    }
}
