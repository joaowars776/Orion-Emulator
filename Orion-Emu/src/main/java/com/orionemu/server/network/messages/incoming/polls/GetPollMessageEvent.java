package com.orionemu.server.network.messages.incoming.polls;

import com.orionemu.server.game.polls.PollManager;
import com.orionemu.server.game.polls.types.Poll;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.polls.PollMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

public class GetPollMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int pollId = msg.readInt();

        Poll poll = PollManager.getInstance().getPollbyId(pollId);

        if(poll == null) {
            return;
        }

        client.send(new PollMessageComposer(poll));
    }
}