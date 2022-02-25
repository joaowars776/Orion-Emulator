package com.orionemu.server.network.messages.outgoing.group.forums;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.List;

public class GroupForumListMessageComposer extends MessageComposer {

    private final int code;
    private final List<Group> groups;
    private final int playerId;

    public GroupForumListMessageComposer(final int code, final List<Group> groups, final int playerId) {
        this.code = code;
        this.groups = groups;
        this.playerId = playerId;
    }

    @Override
    public short getId() {
        return Composers.ForumsListDataMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.code);
        msg.writeInt(this.groups.size());
        msg.writeInt(0);
        msg.writeInt(this.groups.size()); //???

        for(Group group : this.groups) {
            group.getForumComponent().composeData(msg);
        }
    }
}
