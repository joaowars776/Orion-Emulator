package com.orionemu.server.network.messages.incoming.room.item.stickies;

import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.rooms.objects.items.RoomItemWall;
import com.orionemu.server.game.rooms.objects.items.types.wall.PostItWallItem;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class SavePostItMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int virtualId = msg.readInt();

        long itemId = ItemManager.getInstance().getItemIdByVirtualId(virtualId);

        if (client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) {
            return;
        }

        Room room = client.getPlayer().getEntity().getRoom();

        String colour = msg.readString();
        String message = msg.readString();

        RoomItemWall wallItem = room.getItems().getWallItem(itemId);

        if (wallItem == null || !(wallItem instanceof PostItWallItem)) return;

        if (!client.getPlayer().getEntity().getRoom().getRights().hasRights(client.getPlayer().getId()) && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            return;
        }

        wallItem.setExtraData(colour + " " + message);
        wallItem.sendUpdate();

        wallItem.saveData();
    }
}
