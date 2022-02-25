package com.orionemu.server.network.messages.outgoing.group.forums;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.groups.types.components.forum.threads.ForumThreadReply;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class GroupForumPostReplyMessageComposer extends MessageComposer {
    private int groupId;
    private int threadId;
    private ForumThreadReply reply;

    public GroupForumPostReplyMessageComposer(int groupId, int threadId, ForumThreadReply reply) {
        this.groupId = groupId;
        this.threadId = threadId;
        this.reply = reply;
    }

    @Override
    public short getId() {
        return Composers.ThreadReplyMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.groupId);
        msg.writeInt(this.threadId);

        this.reply.compose(msg);
    }
}
