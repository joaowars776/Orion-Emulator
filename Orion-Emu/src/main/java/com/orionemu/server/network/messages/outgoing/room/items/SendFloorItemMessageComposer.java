package com.orionemu.server.network.messages.outgoing.room.items;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class SendFloorItemMessageComposer extends MessageComposer {
    private final RoomItemFloor itemFloor;

    public SendFloorItemMessageComposer(RoomItemFloor itemFloor) {
        this.itemFloor = itemFloor;
    }

    @Override
    public short getId() {
        return Composers.ObjectAddMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        this.itemFloor.serialize(msg, true);
    }
}
