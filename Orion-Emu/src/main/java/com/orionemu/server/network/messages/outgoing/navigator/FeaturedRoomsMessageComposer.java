package com.orionemu.server.network.messages.outgoing.navigator;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.navigator.types.featured.FeaturedRoom;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.Map;


public class FeaturedRoomsMessageComposer extends MessageComposer {
    private final Map<Integer, FeaturedRoom> featuredRooms;

    public FeaturedRoomsMessageComposer(Map<Integer, FeaturedRoom> featuredRooms) {
        this.featuredRooms = featuredRooms;
    }

    @Override
    public short getId() {
        return Composers.FeaturedRoomsMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(featuredRooms.size());

        for (FeaturedRoom room : featuredRooms.values()) {
            if (!room.isCategory()) {
                continue;
            }

            room.compose(msg);

            for (FeaturedRoom room1 : featuredRooms.values()) {
                if (room1.getCategoryId() != room.getId()) {
                    continue;
                }

                room1.compose(msg);
            }
        }

        for (FeaturedRoom room : featuredRooms.values()) {
            if (!room.isCategory() && room.isRecommended()) {
                msg.writeInt(1);
                room.compose(msg);
                msg.writeInt(0);

                return;
            }
        }

        msg.writeInt(0);
        msg.writeInt(0);
    }
}
