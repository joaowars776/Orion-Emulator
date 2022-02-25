package com.orionemu.server.network.messages.outgoing.room.engine;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class RoomForwardMessageComposer extends MessageComposer {
    private final int roomId;

    public RoomForwardMessageComposer(final int roomId) {
        this.roomId = roomId;
    }

    @Override
    public short getId() {
        return Composers.RoomForwardMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(false);
        msg.writeInt(roomId);
    }
}
