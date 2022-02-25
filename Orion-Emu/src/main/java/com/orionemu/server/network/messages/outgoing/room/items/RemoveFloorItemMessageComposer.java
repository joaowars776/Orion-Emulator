package com.orionemu.server.network.messages.outgoing.room.items;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class RemoveFloorItemMessageComposer extends MessageComposer {
    private final int id;
    private final int ownerId;
    private final int delay;

    public RemoveFloorItemMessageComposer(final int id, final int ownerId, final int delay) {
        this.id = id;
        this.ownerId = ownerId;
        this.delay = delay;
    }

    public RemoveFloorItemMessageComposer(final int id, final int ownerId) {
        this(id, ownerId, 0);
    }

    @Override
    public short getId() {
        return Composers.ObjectRemoveMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.id);
        msg.writeBoolean(false); // Is expired
        msg.writeInt(this.ownerId); // Picker ID
        msg.writeInt(this.delay);
    }
}
