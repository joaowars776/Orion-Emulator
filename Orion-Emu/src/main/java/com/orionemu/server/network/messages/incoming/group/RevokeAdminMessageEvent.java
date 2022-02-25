package com.orionemu.server.network.messages.incoming.group;

import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.game.groups.types.GroupAccessLevel;
import com.orionemu.server.game.groups.types.GroupMember;
import com.orionemu.server.game.rooms.objects.entities.RoomEntityStatus;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.group.GroupMembersMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.permissions.YouAreControllerMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

import java.util.ArrayList;


public class RevokeAdminMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int groupId = msg.readInt();
        int playerId = msg.readInt();

        if (!client.getPlayer().getGroups().contains(groupId)) {
            return;
        }

        Group group = GroupManager.getInstance().get(groupId);

        if (group == null)
            return;

        if (!group.getMembershipComponent().getMembers().containsKey(playerId))
            return;

        GroupMember groupMember = group.getMembershipComponent().getMembers().get(playerId);

        if (groupMember == null)
            return;

        if (!groupMember.getAccessLevel().isAdmin())
            return;

        groupMember.setAccessLevel(GroupAccessLevel.MEMBER);
        groupMember.save();

        if (!group.getData().canMembersDecorate()) {
            Session session = NetworkManager.getInstance().getSessions().getByPlayerId(groupMember.getPlayerId());

            if (session != null && session.getPlayer().getEntity() != null && session.getPlayer().getEntity().getRoom().getId() == group.getData().getRoomId()) {
                session.send(new YouAreControllerMessageComposer(0));
                session.getPlayer().getEntity().removeStatus(RoomEntityStatus.CONTROLLER);
                session.getPlayer().getEntity().addStatus(RoomEntityStatus.CONTROLLER, "0");
                session.getPlayer().getEntity().markNeedsUpdate();
            }
        }

        group.getMembershipComponent().getAdministrators().remove(groupMember.getPlayerId());
        client.send(new GroupMembersMessageComposer(group.getData(), 0, new ArrayList<>(group.getMembershipComponent().getAdministrators()), 1, "", group.getMembershipComponent().getAdministrators().contains(client.getPlayer().getId())));
    }
}
