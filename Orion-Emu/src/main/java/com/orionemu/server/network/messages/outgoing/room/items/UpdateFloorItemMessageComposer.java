package com.orionemu.server.network.messages.outgoing.room.items;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class UpdateFloorItemMessageComposer extends MessageComposer {
    private final RoomItemFloor item;

    public UpdateFloorItemMessageComposer(RoomItemFloor item) {
        this.item = item;
    }

    @Override
    public short getId() {
        return Composers.ObjectUpdateMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        this.item.serialize(msg);
        msg.writeInt(this.item.getOwner());
    }
}
