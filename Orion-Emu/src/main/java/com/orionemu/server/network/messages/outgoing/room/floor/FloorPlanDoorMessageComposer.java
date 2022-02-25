package com.orionemu.server.network.messages.outgoing.room.floor;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class FloorPlanDoorMessageComposer extends MessageComposer {
    private final int x;
    private final int y;
    private final int rotation;

    public FloorPlanDoorMessageComposer(final int x, final int y, final int rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    @Override
    public short getId() {
        return Composers.FloorPlanSendDoorMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(x);
        msg.writeInt(y);
        msg.writeInt(rotation);
    }
}
