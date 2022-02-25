package com.orionemu.server.network.messages.outgoing.quiz;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

/**
 * Created by Emrik on 2017-07-12.
 */
public class QuizDataMessageComposer extends MessageComposer {
    private String Type;

    public QuizDataMessageComposer(String type) {
        super();
        this.Type = type;
    }

    @Override
    public void compose(IComposer msg) {
    msg.writeString(this.Type);
    msg.writeInt(5);
        msg.writeInt(5);
        msg.writeInt(7);
        msg.writeInt(0);
        msg.writeInt(1);
        msg.writeInt(6);

    }

    @Override
    public short getId() {
        return Composers.QuizDataMessageComposer;
    }
}
