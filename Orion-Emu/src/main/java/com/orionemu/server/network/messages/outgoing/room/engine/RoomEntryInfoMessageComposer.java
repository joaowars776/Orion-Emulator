package com.orionemu.server.network.messages.outgoing.room.engine;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class RoomEntryInfoMessageComposer extends MessageComposer {
    private final int id;
    private final boolean hasOwnershipPermission;

    public RoomEntryInfoMessageComposer(final int id, final boolean hasOwnershipPermission) {
        this.id = id;
        this.hasOwnershipPermission = hasOwnershipPermission;
    }

    @Override
    public short getId() {
        return Composers.RoomEntryInfoMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(true);
        msg.writeInt(id);
        msg.writeBoolean(hasOwnershipPermission);
    }
}
