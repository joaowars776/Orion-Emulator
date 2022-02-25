package com.orionemu.server.network.messages.incoming.room.item.mannequins;

import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.objects.items.types.floor.boutique.MannequinFloorItem;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.items.UpdateFloorItemMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class SaveMannequinMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int id = msg.readInt();

        final long itemId = ItemManager.getInstance().getItemIdByVirtualId(id);

        Room room = client.getPlayer().getEntity().getRoom();

        if (room == null || !room.getRights().hasRights(client.getPlayer().getId()) && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            return;
        }

        RoomItemFloor item = room.getItems().getFloorItem(itemId);

        if (item == null) {
            return;
        }

        ((MannequinFloorItem) item).setName(msg.readString());
        ((MannequinFloorItem) item).setGender(client.getPlayer().getData().getGender());

        room.getEntities().broadcastMessage(new UpdateFloorItemMessageComposer(item));
        item.saveData();
    }
}
