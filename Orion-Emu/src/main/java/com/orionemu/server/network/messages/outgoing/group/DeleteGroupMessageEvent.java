package com.orionemu.server.network.messages.outgoing.group;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.objects.items.RoomItem;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.objects.items.RoomItemWall;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.storage.queries.rooms.RoomItemDao;
import com.google.common.collect.Lists;

import java.util.List;

public class DeleteGroupMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int groupId = msg.readInt();

        if (GroupManager.getInstance().get(groupId) != null) {
            Group group = GroupManager.getInstance().get(groupId);
            Room room = RoomManager.getInstance().get(group.getData().getRoomId());

            if(group.getData().getOwnerId() != client.getPlayer().getId()) {
                return;
            }

            for (Integer groupMemberId : group.getMembershipComponent().getMembers().keySet()) {
                Session groupMemberSession = NetworkManager.getInstance().getSessions().getByPlayerId(groupMemberId);

                List<RoomItem> floorItemsOwnedByPlayer = Lists.newArrayList();

                if (groupMemberId != group.getData().getOwnerId()) {
                    for (RoomItemFloor floorItem : room.getItems().getFloorItems().values()) {
                        if (floorItem.getOwner() == groupMemberId) {
                            floorItemsOwnedByPlayer.add(floorItem);
                        }
                    }

                    for (RoomItemWall wallItem : room.getItems().getWallItems().values()) {
                        if (wallItem.getOwner() == groupMemberId) {
                            floorItemsOwnedByPlayer.add(wallItem);
                        }
                    }
                }

                if (groupMemberSession != null && groupMemberSession.getPlayer() != null) {
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
                        RoomItemDao.removeItemFromRoom(roomItem.getId(), groupMemberId, roomItem.getExtraData());
                    }
                }

                floorItemsOwnedByPlayer.clear();
            }

            client.send(new AlertMessageComposer(Locale.getOrDefault("command.deletegroup.done", "The group was deleted successfully.")));
            GroupManager.getInstance().removeGroup(group.getId());

            room.setGroup(null);

            room.getData().setGroupId(0);
            room.getData().save();

            room.setIdleNow();
        }
    }
}
