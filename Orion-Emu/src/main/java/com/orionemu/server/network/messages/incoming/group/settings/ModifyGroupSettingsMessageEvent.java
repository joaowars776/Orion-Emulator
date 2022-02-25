package com.orionemu.server.network.messages.incoming.group.settings;

import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.game.groups.types.GroupType;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.engine.RoomDataMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class ModifyGroupSettingsMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int groupId = msg.readInt();

        if (!client.getPlayer().getGroups().contains(groupId))
            return;

        Group group = GroupManager.getInstance().get(groupId);

        if (group == null || group.getData().getOwnerId() != client.getPlayer().getId())
            return;

        int type = msg.readInt();
        int rightsType = msg.readInt();

        if (GroupType.valueOf(type) != group.getData().getType()) {
            group.getMembershipComponent().clearRequests();
        }

        group.getData().setType(GroupType.valueOf(type));

        // 0 = members, 1 = admins only.
        group.getData().setCanMembersDecorate(rightsType == 0);

        group.getData().save();
        group.commit();

        if (RoomManager.getInstance().isActive(group.getData().getRoomId())) {
            Room room = RoomManager.getInstance().get(group.getData().getRoomId());

//            room.getEntities().broadcastMessage(new RoomInfoUpdatedMessageComposer(room.getId()));
//            room.getEntities().broadcastMessage(new SettingsUpdatedMessageComposer(room.getId()));
            room.getEntities().broadcastMessage(new RoomDataMessageComposer(room, false, false, false));
        }
    }
}
