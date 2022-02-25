package com.orionemu.server.network.messages.outgoing.room.items;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.rooms.objects.items.RoomItemWall;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class SendWallItemMessageComposer extends MessageComposer {
    private final RoomItemWall itemWall;

    public SendWallItemMessageComposer(RoomItemWall itemWall) {
        this.itemWall = itemWall;
    }

    @Override
    public short getId() {
        return Composers.ItemAddMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        this.itemWall.serialize(msg);
    }
}
