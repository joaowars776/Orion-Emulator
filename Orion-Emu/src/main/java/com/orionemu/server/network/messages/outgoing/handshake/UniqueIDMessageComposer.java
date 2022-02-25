package com.orionemu.server.network.messages.outgoing.handshake;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class UniqueIDMessageComposer extends MessageComposer {
    private final String uniqueId;

    public UniqueIDMessageComposer(final String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public short getId() {
        return Composers.UniqueMachineIDMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.uniqueId);
    }
}
