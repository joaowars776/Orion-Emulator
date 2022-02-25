package com.orionemu.server.network.messages.outgoing.room.avatar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class TypingStatusMessageComposer extends MessageComposer {
    private final int playerId;
    private final int status;

    public TypingStatusMessageComposer(final int playerId, final int status) {
        this.playerId = playerId;
        this.status = status;
    }

    @Override
    public short getId() {
        return Composers.UserTypingMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(playerId);
        msg.writeInt(status);
    }
}
