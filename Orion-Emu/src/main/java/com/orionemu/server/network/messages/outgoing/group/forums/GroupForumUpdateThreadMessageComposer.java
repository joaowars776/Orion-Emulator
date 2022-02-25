package com.orionemu.server.network.messages.outgoing.group.forums;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.groups.types.components.forum.threads.ForumThread;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class GroupForumUpdateThreadMessageComposer extends MessageComposer {
    private int groupId;
    private ForumThread forumThread;

    public GroupForumUpdateThreadMessageComposer(int groupId, ForumThread forumThread) {
        this.groupId = groupId;
        this.forumThread = forumThread;
    }

    @Override
    public short getId() {
        return Composers.ThreadUpdatedMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.groupId);

        this.forumThread.compose(msg);
    }
}
