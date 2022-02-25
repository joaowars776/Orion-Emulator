package com.orionemu.server.network.messages.outgoing.user;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class nuxInstructionComposer extends MessageComposer {

    private String instruction;

    public nuxInstructionComposer(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public short getId() {
        return Composers.WardrobeMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeString(instruction);
    }
}
