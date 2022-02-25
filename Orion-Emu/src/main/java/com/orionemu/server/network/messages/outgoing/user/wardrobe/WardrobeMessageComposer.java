package com.orionemu.server.network.messages.outgoing.user.wardrobe;

import com.orionemu.api.game.players.data.types.IWardrobeItem;
import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.List;


public class WardrobeMessageComposer extends MessageComposer {
    private final List<IWardrobeItem> wardrobe;

    public WardrobeMessageComposer(final List<IWardrobeItem> wardrobe) {
        this.wardrobe = wardrobe;
    }

    @Override
    public short getId() {
        return Composers.WardrobeMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(1);
        msg.writeInt(wardrobe.size());

        for (IWardrobeItem item : wardrobe) {
            msg.writeInt(item.getSlot());
            msg.writeString(item.getFigure());

            if (item.getGender() != null) {
                msg.writeString(item.getGender().toUpperCase());
            } else {
                msg.writeString("M");
            }
        }
    }
}
