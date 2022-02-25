package com.orionemu.server.network.messages.incoming.group.forum.data;

import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.game.groups.types.components.forum.settings.ForumPermission;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.group.forums.GroupForumDataMessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

public class ForumDataMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int groupId = msg.readInt();

        Group group = GroupManager.getInstance().get(groupId);

        if(group == null || group.getData() == null || !group.getData().hasForum()) {
            return;
        }

        if (group.getForumComponent().getForumSettings().getReadPermission() == ForumPermission.MEMBERS) {
            if (!group.getMembershipComponent().getMembers().containsKey(client.getPlayer().getId())) {
                client.send(new NotificationMessageComposer("forums.error.access_denied"));
                return;
            }
        } else if (group.getForumComponent().getForumSettings().getReadPermission() == ForumPermission.ADMINISTRATORS) {
            if (!group.getMembershipComponent().getAdministrators().contains(client.getPlayer().getId())) {
                client.send(new NotificationMessageComposer("forums.error.access_denied"));
                return;
            }
        }

        client.send(new GroupForumDataMessageComposer(group, client.getPlayer().getId()));
    }
}
