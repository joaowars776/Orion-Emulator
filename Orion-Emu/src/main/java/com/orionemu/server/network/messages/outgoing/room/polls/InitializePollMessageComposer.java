package com.orionemu.server.network.messages.outgoing.room.polls;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class InitializePollMessageComposer extends MessageComposer {

    private final int pollId;
    private final String headline;
    private final String thanksMessage;

    public InitializePollMessageComposer(int pollId, String headline, String thanksMessage) {
        this.pollId = pollId;
        this.headline = headline;
        this.thanksMessage = thanksMessage;
    }

    @Override
    public short getId() {
        return Composers.InitializePollMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.pollId);

        msg.writeString(this.headline);
        msg.writeString(this.headline);
        msg.writeString(this.headline);
        msg.writeString(this.thanksMessage);
    }
}
