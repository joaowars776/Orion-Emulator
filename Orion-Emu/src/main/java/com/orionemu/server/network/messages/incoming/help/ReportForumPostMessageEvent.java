package com.orionemu.server.network.messages.incoming.help;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

public class ReportForumPostMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {

    }
}
