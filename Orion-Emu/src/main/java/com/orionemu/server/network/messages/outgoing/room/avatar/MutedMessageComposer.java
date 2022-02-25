package com.orionemu.server.network.messages.outgoing.room.avatar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class MutedMessageComposer extends MessageComposer {
    private final int secondsLeft;

    public MutedMessageComposer(final int secondsLeft) {
        this.secondsLeft = secondsLeft;
    }

    @Override
    public short getId() {
        return Composers.MutedMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(secondsLeft);
    }
}
