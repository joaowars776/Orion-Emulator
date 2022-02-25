package com.orionemu.server.network.messages.outgoing.room.engine;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class OpenConnectionMessageComposer extends MessageComposer {
    @Override
    public short getId() {
        return Composers.OpenConnectionMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {

    }
}
