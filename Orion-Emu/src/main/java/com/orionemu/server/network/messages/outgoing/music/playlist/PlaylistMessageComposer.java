package com.orionemu.server.network.messages.outgoing.music.playlist;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.items.music.SongItemData;
import com.orionemu.server.game.rooms.objects.items.types.floor.SoundMachineFloorItem;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.List;

public class PlaylistMessageComposer extends MessageComposer {
    private List<SongItemData> songItemDatas;

    public PlaylistMessageComposer(List<SongItemData> songItemDatas) {
        this.songItemDatas = songItemDatas;
    }

    @Override
    public short getId() {
        return Composers.PlaylistMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(SoundMachineFloorItem.MAX_CAPACITY);
        msg.writeInt(songItemDatas.size());

        for (SongItemData songItemData : this.songItemDatas) {
            msg.writeInt(songItemData.getItemSnapshot().getBaseItemId());
            msg.writeInt(songItemData.getSongId());
        }
    }
}
