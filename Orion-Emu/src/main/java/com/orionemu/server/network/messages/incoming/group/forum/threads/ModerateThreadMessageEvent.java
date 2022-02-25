package com.orionemu.server.network.messages.incoming.group.forum.threads;

import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.game.groups.types.components.forum.settings.ForumPermission;
import com.orionemu.server.game.groups.types.components.forum.settings.ForumSettings;
import com.orionemu.server.game.groups.types.components.forum.threads.ForumThread;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.group.forums.GroupForumUpdateThreadMessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.storage.queries.groups.GroupForumThreadDao;

public class ModerateThreadMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final int groupId = msg.readInt();
        final int threadId = msg.readInt();
        final int state = msg.readInt();

        Group group = GroupManager.getInstance().get(groupId);

        if (group == null || !group.getData().hasForum()) {
            return;
        }

        ForumSettings forumSettings = group.getForumComponent().getForumSettings();

        if (forumSettings.getModeratePermission() == ForumPermission.OWNER) {
            if (client.getPlayer().getId() != group.getData().getId()) {
                return;
            }
        } else {
            if (!group.getMembershipComponent().getAdministrators().contains(client.getPlayer().getId())) {
                return;
            }
        }

        ForumThread forumThread = group.getForumComponent().getForumThreads().get(threadId);

        if (forumThread == null) {
            return;
        }

        forumThread.setState(state);
        GroupForumThreadDao.saveMessageState(forumThread.getId(), state, client.getPlayer().getId(), client.getPlayer().getData().getUsername());

        client.send(new NotificationMessageComposer(state == 20 ? "forums.thread.hidden" : "forums.thread.restored"));
        client.send(new GroupForumUpdateThreadMessageComposer(groupId, forumThread));
    }
}
