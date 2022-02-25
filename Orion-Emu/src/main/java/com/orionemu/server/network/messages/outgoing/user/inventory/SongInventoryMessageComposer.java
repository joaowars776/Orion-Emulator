package com.orionemu.server.network.messages.outgoing.user.inventory;

import com.orionemu.api.game.furniture.types.SongItem;
import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.List;

public class SongInventoryMessageComposer extends MessageComposer {

    private List<SongItem> songItems;

    public SongInventoryMessageComposer(List<SongItem> songItems) {
        this.songItems = songItems;
    }

    @Override
    public short getId() {
        return Composers.SongInventoryMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.songItems.size());

        for (SongItem songItem : this.songItems) {
            msg.writeInt(ItemManager.getInstance().getItemVirtualId(songItem.getItemSnapshot().getId()));
            msg.writeInt(songItem.getSongId());
        }
    }
}
