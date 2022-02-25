package com.orionemu.server.network.messages.incoming.group.forum.threads;

import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.game.groups.types.components.forum.settings.ForumPermission;
import com.orionemu.server.game.groups.types.components.forum.threads.ForumThread;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.group.forums.GroupForumViewThreadMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

public class ViewThreadMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int groupId = msg.readInt();
        int threadId = msg.readInt();
        int indexStart = msg.readInt();

        final Group group = GroupManager.getInstance().get(groupId);

        if(group == null) {
            return;
        }

        ForumThread forumThread = group.getForumComponent().getForumThreads().get(threadId);

        if(forumThread == null) {
            return;
        }

        if(group.getForumComponent().getForumSettings().getReadPermission() == ForumPermission.MEMBERS) {
            if(!group.getMembershipComponent().getMembers().containsKey(client.getPlayer().getId())) {
                return;
            }
        } else if(group.getForumComponent().getForumSettings().getReadPermission() == ForumPermission.ADMINISTRATORS) {
            if(!group.getMembershipComponent().getAdministrators().contains(client.getPlayer().getId())) {
                return;
            }
        }

        if(forumThread.getState() != 1) {
            // TODO: do the shizzle.
            return;
        }

        client.send(new GroupForumViewThreadMessageComposer(group.getData(), threadId, forumThread.getReplies(indexStart), indexStart));
    }
}
