package com.orionemu.server.network.messages.outgoing.room.permissions;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class FloodFilterMessageComposer extends MessageComposer {
    private final double seconds;

    public FloodFilterMessageComposer(double seconds) {
        this.seconds = seconds;
    }

    @Override
    public short getId() {
        return Composers.FloodControlMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(((int) Math.round(this.seconds)));
    }
}
