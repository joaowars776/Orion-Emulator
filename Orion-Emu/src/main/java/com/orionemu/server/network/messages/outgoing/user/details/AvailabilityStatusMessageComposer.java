package com.orionemu.server.network.messages.outgoing.user.details;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class AvailabilityStatusMessageComposer extends MessageComposer {

    @Override
    public short getId() {
        return Composers.AvailabilityStatusMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(false);
        msg.writeBoolean(false);
        msg.writeBoolean(true);
    }
}
