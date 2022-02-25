package com.orionemu.server.network.messages.incoming.room.item;

import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.rooms.objects.items.RoomItemWall;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class UseWallItemMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) {
        int virtualId = msg.readInt();

        Long itemId = ItemManager.getInstance().getItemIdByVirtualId(virtualId);

        if(itemId == null) {
            return;
        }

        if (client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) {
            return;
        }

        RoomItemWall item = client.getPlayer().getEntity().getRoom().getItems().getWallItem(itemId);

        if (item == null) {
            return;
        }

        int requestData = msg.readInt();

        if(!client.getPlayer().getEntity().isVisible()) {
            return;
        }

        item.onInteract(client.getPlayer().getEntity(), requestData, false);
    }
}
