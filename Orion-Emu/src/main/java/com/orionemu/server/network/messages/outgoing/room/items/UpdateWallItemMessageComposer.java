package com.orionemu.server.network.messages.outgoing.room.items;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.rooms.objects.items.RoomItemWall;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class UpdateWallItemMessageComposer extends MessageComposer {
    private final RoomItemWall item;
    private final int ownerId;
    private final String owner;

    public UpdateWallItemMessageComposer(RoomItemWall item, int ownerId, String owner) {
        this.item = item;
        this.owner = owner;
        this.ownerId = ownerId;
    }

    @Override
    public short getId() {
        return Composers.ItemUpdateMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeString(item.getVirtualId());
        msg.writeInt(item.getDefinition().getSpriteId());
        msg.writeString(item.getWallPosition());
        msg.writeString(item.getExtraData());
        msg.writeInt(!item.getDefinition().getInteraction().equals("default") ? 1 : 0);
        msg.writeInt(ownerId);
        msg.writeString(owner);
    }
}
