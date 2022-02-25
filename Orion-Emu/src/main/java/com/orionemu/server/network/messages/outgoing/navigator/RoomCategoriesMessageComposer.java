package com.orionemu.server.network.messages.outgoing.navigator;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.navigator.types.Category;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.List;
import java.util.Map;


public class RoomCategoriesMessageComposer extends MessageComposer {
    private final Map<Integer, Category> categories;
    private final int rank;

    public RoomCategoriesMessageComposer(final Map<Integer, Category> categories, final int rank) {
        this.categories = categories;
        this.rank = rank;
    }

    @Override
    public short getId() {
        return Composers.UserFlatCatsMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.categories.size());

        for (Category cat : this.categories.values()) {
            msg.writeInt(cat.getId());
            msg.writeString(cat.getPublicName());
            msg.writeBoolean(cat.getRequiredRank() <= this.rank);
        }
    }
}
