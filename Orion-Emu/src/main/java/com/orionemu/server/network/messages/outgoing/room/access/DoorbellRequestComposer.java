package com.orionemu.server.network.messages.outgoing.room.access;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class DoorbellRequestComposer extends MessageComposer {
    private final String username;

    public DoorbellRequestComposer(final String username) {
        this.username = username;
    }

    @Override
    public short getId() {
        return Composers.DoorbellMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.username);
    }
}
