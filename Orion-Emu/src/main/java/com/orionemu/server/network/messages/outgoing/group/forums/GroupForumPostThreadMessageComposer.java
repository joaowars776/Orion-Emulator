package com.orionemu.server.network.messages.outgoing.group.forums;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.groups.types.components.forum.threads.ForumThread;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class GroupForumPostThreadMessageComposer extends MessageComposer {
    private int groupId;
    private ForumThread forumThread;

    public GroupForumPostThreadMessageComposer(int groupId, ForumThread forumThread) {
        this.groupId = groupId;
        this.forumThread = forumThread;
    }

    @Override
    public short getId() {
        return Composers.ThreadCreatedMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(groupId);

        forumThread.compose(msg);
    }
}
