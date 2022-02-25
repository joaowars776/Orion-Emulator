package com.orionemu.server.network.messages.outgoing.room.alerts;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class CantConnectMessageComposer extends MessageComposer {
    private final int Error;

    public CantConnectMessageComposer(final int Error) {
        this.Error = Error;
    }

    @Override
    public short getId() {
        return Composers.CantConnectMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(Error);
    }
}
