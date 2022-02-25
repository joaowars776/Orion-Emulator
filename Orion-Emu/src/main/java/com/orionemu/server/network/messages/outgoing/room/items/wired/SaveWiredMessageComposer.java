package com.orionemu.server.network.messages.outgoing.room.items.wired;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class SaveWiredMessageComposer extends MessageComposer {
    @Override
    public short getId() {
        return Composers.HideWiredConfigMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {

    }
}
