package com.orionemu.server.game.commands.user.group;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.game.rooms.objects.items.RoomItem;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.objects.items.RoomItemWall;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.rooms.RoomItemDao;
import com.google.common.collect.Lists;

import java.util.List;

public class EjectAllCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        Room room = client.getPlayer().getEntity().getRoom();
        Group group = room.getGroup();

        if (room.getData().getOwnerId() != client.getPlayer().getId()) {
            final List<RoomItem> itemsToRemove = Lists.newArrayList();

            for(RoomItemFloor roomItemFloor : client.getPlayer().getEntity().getRoom().getItems().getFloorItems().values()) {
                if(roomItemFloor.getOwner() == client.getPlayer().getId()) {
                    itemsToRemove.add(roomItemFloor);
                }
            }

            for(RoomItemWall roomItemWall : client.getPlayer().getEntity().getRoom().getItems().getWallItems().values()) {
                if(roomItemWall.getOwner() == client.getPlayer().getId()) {
                    itemsToRemove.add(roomItemWall);
                }
            }

            for(RoomItem item : itemsToRemove) {
                if(item instanceof RoomItemFloor) {
                    client.getPlayer().getEntity().getRoom().getItems().removeItem((RoomItemFloor) item, client);
                } else {
                    client.getPlayer().getEntity().getRoom().getItems().removeItem(((RoomItemWall) item), client, true);
                }
            }
        } else {
            for (Integer playerWithItem : room.getItems().getItemOwners().keySet()) {
                Session groupMemberSession = NetworkManager.getInstance().getSessions().getByPlayerId(playerWithItem);

                List<RoomItem> floorItemsOwnedByPlayer = Lists.newArrayList();

                for (RoomItemFloor floorItem : room.getItems().getFloorItems().values()) {

                    if (floorItem.getOwner() == playerWithItem) {
                        floorItemsOwnedByPlayer.add(floorItem);
                    }
                }

                for (RoomItemWall wallItem : room.getItems().getWallItems().values()) {
                    if (wallItem.getOwner() == playerWithItem) {
                        floorItemsOwnedByPlayer.add(wallItem);
                    }
                }

                if (groupMemberSession != null && groupMemberSession.getPlayer() != null && group != null) {
                    groupMemberSession.getPlayer().getGroups().remove(new Integer(group.getId()));

                    if (groupMemberSession.getPlayer().getData().getFavouriteGroup() == group.getId()) {
                        groupMemberSession.getPlayer().getData().setFavouriteGroup(0);
                    }

                    for (RoomItem roomItem : floorItemsOwnedByPlayer) {
                        if (roomItem instanceof RoomItemFloor)
                            room.getItems().removeItem(((RoomItemFloor) roomItem), groupMemberSession);
                        else if (roomItem instanceof RoomItemWall)
                            room.getItems().removeItem(((RoomItemWall) roomItem), groupMemberSession, true);
                    }
                } else {
                    for (RoomItem roomItem : floorItemsOwnedByPlayer) {
                        RoomItemDao.removeItemFromRoom(roomItem.getId(), playerWithItem, roomItem.getExtraData());
                    }
                }

                floorItemsOwnedByPlayer.clear();
            }
        }
    }

    @Override
    public String getPermission() {
        return "ejectall_command";
    }

    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return Locale.getOrDefault("command.ejectall.description", "Removes all items you own in the room");
    }
}