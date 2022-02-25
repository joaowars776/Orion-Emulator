package com.orionemu.server.network.messages.incoming.music.playlist;

import com.orionemu.server.game.items.music.SongItemData;
import com.orionemu.server.game.rooms.objects.items.types.floor.SoundMachineFloorItem;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.music.playlist.PlaylistMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.inventory.SongInventoryMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.inventory.UpdateInventoryMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.rooms.RoomItemDao;

public class PlaylistRemoveMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int songIndex = msg.readInt();

        if (client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) {
            return;
        }

        Room room = client.getPlayer().getEntity().getRoom();

        if (client.getPlayer().getId() != room.getData().getOwnerId() && !client.getPlayer().getPermissions().getRank().roomFullControl())
            return;

        SoundMachineFloorItem soundMachineFloorItem = room.getItems().getSoundMachine();

        if (soundMachineFloorItem == null) {
            return;
        }

        if (songIndex < 0 || songIndex >= soundMachineFloorItem.getSongs().size()) {
            return;
        }

        SongItemData songItemData = soundMachineFloorItem.removeSong(songIndex);
        soundMachineFloorItem.saveData();

        RoomItemDao.removeItemFromRoom(songItemData.getItemSnapshot().getId(), client.getPlayer().getId(), songItemData.getItemSnapshot().getExtraData());
        client.getPlayer().getInventory().add(songItemData.getItemSnapshot().getId(), songItemData.getItemSnapshot().getBaseItemId(), songItemData.getItemSnapshot().getExtraData(), null);

        client.send(new UpdateInventoryMessageComposer());

        client.send(new SongInventoryMessageComposer(client.getPlayer().getInventory().getSongs()));
        client.send(new PlaylistMessageComposer(soundMachineFloorItem.getSongs()));
    }
}
