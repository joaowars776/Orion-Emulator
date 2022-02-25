package com.orionemu.server.network.messages.incoming.group.forum.data;

import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.game.groups.types.GroupData;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.group.forums.GroupForumListMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.google.common.collect.Lists;

import java.util.List;

public class GetForumsMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        List<Group> myGroups = Lists.newArrayList();

        for(int groupId : client.getPlayer().getGroups()) {
            final GroupData groupData = GroupManager.getInstance().getData(groupId);

            if(groupData != null && groupData.hasForum()) {
                myGroups.add(GroupManager.getInstance().get(groupId));
            }
        }

        client.send(new GroupForumListMessageComposer(msg.readInt(), myGroups, client.getPlayer().getId()));
    }
}
