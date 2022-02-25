package com.orionemu.server.network.messages.incoming.quests;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
/**
 * Created by Emrik on 2017-06-23.
 */
public class GetTalentTrackMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {

    }
}
