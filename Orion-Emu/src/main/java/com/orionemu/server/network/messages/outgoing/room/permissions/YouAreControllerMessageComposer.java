package com.orionemu.server.network.messages.outgoing.room.permissions;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class YouAreControllerMessageComposer extends MessageComposer {
    private final int rightId;

    public YouAreControllerMessageComposer(int rightId) {
        this.rightId = rightId;
    }

    @Override
    public short getId() {
        return Composers.YouAreControllerMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(rightId);
    }
}
