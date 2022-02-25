package com.orionemu.server.network.messages.incoming.room.item.stickies;

import com.orionemu.api.game.players.data.components.inventory.PlayerItem;
import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.rooms.objects.misc.Position;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class PlacePostItMessageEvent implements Event {

    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int virtualId = msg.readInt();

        long itemId = ItemManager.getInstance().getItemIdByVirtualId(virtualId);

        if (client.getPlayer() == null || client.getPlayer().getEntity() == null) {
            return;
        }

        String position = Position.validateWallPosition(msg.readString());

        if (!client.getPlayer().getEntity().getRoom().getRights().hasRights(client.getPlayer().getId())
                && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            return;
        }

        if (position == null) {
            return;
        }

        PlayerItem item = client.getPlayer().getInventory().getWallItem(itemId);

        if (item == null) {
            return;
        }

        client.getPlayer().getEntity().getRoom().getItems().placeWallItem(item, position, client.getPlayer());
    }
}
