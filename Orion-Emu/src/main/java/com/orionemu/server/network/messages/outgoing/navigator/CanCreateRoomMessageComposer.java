package com.orionemu.server.network.messages.outgoing.navigator;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class CanCreateRoomMessageComposer extends MessageComposer {

    @Override
    public short getId() {
        return Composers.CanCreateRoomMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(0);
        msg.writeInt(25);
    }
}
