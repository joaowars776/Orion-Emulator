package com.orionemu.server.network.messages.outgoing.user.inventory;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class UpdateInventoryMessageComposer extends MessageComposer {

    @Override
    public short getId() {
        return Composers.FurniListUpdateMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {

    }
}