package com.orionemu.server.network.messages.incoming.group;

import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.game.groups.types.GroupMember;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.group.GroupMembersMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.PlayerDao;

import java.util.ArrayList;
import java.util.List;


public class GroupMembersMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final int groupId = msg.readInt();
        final int page = msg.readInt();
        final String searchQuery = msg.readString();
        final int requestType = msg.readInt();

        Group group = GroupManager.getInstance().get(groupId);

        if (group == null)
            return;

        List<Object> groupMembers;

        switch (requestType) {
            default:
                groupMembers = new ArrayList<>(group.getMembershipComponent().getMembersAsList());
                break;
            case 1:
                groupMembers = new ArrayList<>(group.getMembershipComponent().getAdministrators());
                break;
            case 2:
                groupMembers = new ArrayList<>(group.getMembershipComponent().getMembershipRequests());
                break;
        }

        if (!searchQuery.isEmpty()) {
            List<Object> toRemove = new ArrayList<>();

            for (Object obj : groupMembers) {
                String username = PlayerDao.getUsernameByPlayerId(obj instanceof GroupMember ? ((GroupMember) obj).getPlayerId() : (int) obj);

                if (username == null) {
                    toRemove.add(obj);
                } else {
                    if (!username.toLowerCase().startsWith(searchQuery.toLowerCase()))
                        toRemove.add(obj);
                }
            }

            for (Object obj : toRemove) {
                groupMembers.remove(obj);
            }

            toRemove.clear();
        }

        client.send(new GroupMembersMessageComposer(group.getData(), page, groupMembers, requestType, searchQuery, group.getMembershipComponent().getAdministrators().contains(client.getPlayer().getId())));
    }
}
