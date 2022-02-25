package com.orionemu.server.network.messages.outgoing.room.items;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class FloorItemsMessageComposer extends MessageComposer {
    private final Room room;

    public FloorItemsMessageComposer(final Room room) {
        this.room = room;
    }

    @Override
    public short getId() {
        return Composers.ObjectsMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        if (room.getItems().getFloorItems().size() > 0) {
            //if (room.getGroup() == null) {
            msg.writeInt(1);
            msg.writeInt(room.getData().getOwnerId());
            msg.writeString(room.getData().getOwner());
            msg.writeInt(room.getItems().getFloorItems().size());

            for (RoomItemFloor item : room.getItems().getFloorItems().values()) {
                item.serialize((msg));
            }

        } else {
            msg.writeInt(0);
            msg.writeInt(0);
        }

    }
}
