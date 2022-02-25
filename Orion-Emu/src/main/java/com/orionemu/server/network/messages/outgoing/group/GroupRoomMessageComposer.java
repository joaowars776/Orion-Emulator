package com.orionemu.server.network.messages.outgoing.group;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class GroupRoomMessageComposer extends MessageComposer {
    private final int roomId;
    private final int groupId;

    public GroupRoomMessageComposer(final int roomId, final int groupId) {
        this.roomId = roomId;
        this.groupId = groupId;
    }

    @Override
    public short getId() {
        return Composers.NewGroupInfoMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(roomId);
        msg.writeInt(groupId);
    }
}
