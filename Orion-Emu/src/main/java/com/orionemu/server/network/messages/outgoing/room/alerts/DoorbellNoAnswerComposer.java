package com.orionemu.server.network.messages.outgoing.room.alerts;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class DoorbellNoAnswerComposer extends MessageComposer {
    @Override
    public short getId() {
        return Composers.FlatAccessDeniedMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeString("");
    }
}
