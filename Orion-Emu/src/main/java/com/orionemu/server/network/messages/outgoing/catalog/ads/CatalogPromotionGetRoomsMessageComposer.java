package com.orionemu.server.network.messages.outgoing.catalog.ads;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.rooms.types.RoomData;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.List;


public class CatalogPromotionGetRoomsMessageComposer extends MessageComposer {
    private final List<RoomData> promotableRooms;

    public CatalogPromotionGetRoomsMessageComposer(final List<RoomData> rooms) {
        this.promotableRooms = rooms;
    }

    @Override
    public short getId() {
        return Composers.PromotableRoomsMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(false);
        msg.writeInt(this.promotableRooms.size());

        for (RoomData data : this.promotableRooms) {
            msg.writeInt(data.getId());
            msg.writeString(data.getName());
            msg.writeBoolean(false);
        }
    }
}
