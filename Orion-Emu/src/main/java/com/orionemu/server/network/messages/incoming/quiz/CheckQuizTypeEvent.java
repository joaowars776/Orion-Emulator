package com.orionemu.server.network.messages.incoming.quiz;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.quiz.QuizDataMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

/**
 * Created by Emrik on 2017-07-12.
 */
public class CheckQuizTypeEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.send(new QuizDataMessageComposer("SafetyChat1"));
    }
}
