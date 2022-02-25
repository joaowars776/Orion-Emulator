package com.orionemu.server.network.messages.outgoing.room.permissions;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class YouAreOwnerMessageComposer extends MessageComposer {
    @Override
    public short getId() {
        return Composers.YouAreOwnerMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {

    }
}
