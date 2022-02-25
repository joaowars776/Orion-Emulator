package com.orionemu.server.network.messages.incoming.room.item;

import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.rooms.objects.items.RoomItemWall;
import com.orionemu.server.game.rooms.objects.misc.Position;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.items.UpdateWallItemMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.rooms.RoomItemDao;


public class ChangeWallItemPositionMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int virtualId = msg.readInt();

        Long itemId = ItemManager.getInstance().getItemIdByVirtualId(virtualId);

        if(itemId == null) {
            return;
        }

        String position = Position.validateWallPosition(msg.readString());

        Room room = client.getPlayer().getEntity().getRoom();

        if (room == null || position == null) {
            return;
        }

        boolean isOwner = client.getPlayer().getId() == room.getData().getOwnerId() || client.getPlayer().getData().getRank() > 5;
        boolean hasRights = room.getRights().hasRights(client.getPlayer().getId());

        if (isOwner || hasRights || client.getPlayer().getPermissions().getRank().roomFullControl()) {
            RoomItemWall item = room.getItems().getWallItem(itemId);

            if (item == null) {
                return;
            }

            RoomItemDao.placeWallItem(room.getId(), position, (item.getExtraData().isEmpty() || item.getExtraData().equals(" ")) ? "0" : item.getExtraData(), item.getId());

            item.setPosition(position);
            room.getEntities().broadcastMessage(new UpdateWallItemMessageComposer(item, room.getData().getOwnerId(), room.getData().getOwner()));
        }
    }
}
