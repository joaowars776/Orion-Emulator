package com.orionemu.server.network.messages.incoming.room.item;

import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.quests.types.QuestType;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.objects.items.RoomItemWall;
import com.orionemu.server.game.rooms.objects.items.types.wall.PostItWallItem;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.items.RemoveWallItemMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class PickUpItemMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        if (client == null || client.getPlayer() == null || client.getPlayer().getEntity() == null) {
            return;
        }

        boolean isFloorItem = msg.readInt() == 2;

        Long id = ItemManager.getInstance().getItemIdByVirtualId(msg.readInt());

        if(id == null) {
            return;
        }

        Room room = client.getPlayer().getEntity().getRoom();

        if (room == null) {
            return;
        }

        boolean eject = false;

        RoomItemFloor item = room.getItems().getFloorItem(id);

        if (!room.getRights().hasRights(client.getPlayer().getId()) && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            return;
        }

        if (item == null) {
            RoomItemWall wItem = room.getItems().getWallItem(id);

            if (wItem == null || wItem instanceof PostItWallItem) {
                return;
            }

            if (wItem.getOwner() != client.getPlayer().getId() && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
                if (wItem.getRoom().getData().getOwnerId() != client.getPlayer().getId() && !client.getPlayer().getPermissions().getRank().roomFullControl())
                    return;

                eject = true;
            }

            if (!eject) {
                room.getItems().removeItem(wItem, client.getPlayer().getId(), client);
            } else {
                Session owner = NetworkManager.getInstance().getSessions().getByPlayerId(wItem.getOwner());
                room.getItems().removeItem(wItem, wItem.getOwner(), owner);
            }

            client.send(new RemoveWallItemMessageComposer(wItem.getVirtualId(), client.getPlayer().getId()));
            return;
        }

        if (item.getOwner() != client.getPlayer().getId() && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            if (item.getRoom().getData().getOwnerId() != client.getPlayer().getId() && !client.getPlayer().getPermissions().getRank().roomFullControl())
                return;

            eject = true;
        }

        item.onPickup();

        if (!eject) {
            room.getItems().removeItem(item, client);
        } else {
            Session owner = NetworkManager.getInstance().getSessions().getByPlayerId(item.getOwner());
            room.getItems().removeItem(item, owner);
        }

        client.getPlayer().getQuests().progressQuest(QuestType.FURNI_PICK);
    }
}