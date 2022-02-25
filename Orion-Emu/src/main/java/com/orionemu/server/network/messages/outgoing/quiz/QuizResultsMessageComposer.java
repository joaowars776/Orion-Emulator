package com.orionemu.server.network.messages.outgoing.quiz;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

/**
 * Created by Emrik on 2017-07-12.
 */
public class QuizResultsMessageComposer extends MessageComposer {
    @Override
    public void compose(IComposer msg) {

    }

    @Override
    public short getId() {
        return Composers.QuizResultsMessageComposer;
    }
}
