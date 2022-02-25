package com.orionemu.server.network.messages.incoming.group;

import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.objects.items.types.floor.groups.GroupFloorItem;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.group.GroupFurnitureWidgetMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class GroupFurnitureWidgetMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int virtualId = msg.readInt();

        long itemId = ItemManager.getInstance().getItemIdByVirtualId(virtualId);

        if (client.getPlayer().getEntity() != null && client.getPlayer().getEntity().getRoom() != null) {
            RoomItemFloor floorItem = client.getPlayer().getEntity().getRoom().getItems().getFloorItem(itemId);

            if (floorItem != null && floorItem instanceof GroupFloorItem) {
                Group group = GroupManager.getInstance().get(((GroupFloorItem) floorItem).getGroupId());

                if (group != null) {
                    client.send(new GroupFurnitureWidgetMessageComposer(virtualId, group.getId(), group.getData().getTitle(), group.getData().getRoomId(), client.getPlayer().getGroups().contains(group.getId()), false));
                }
            }
        }
    }
}
