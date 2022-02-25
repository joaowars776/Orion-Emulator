package com.orionemu.server.network.messages.incoming.help.guides;

import com.orionemu.server.game.guides.types.HelpRequest;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

public class GuideHandleHelpRequestMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final HelpRequest helpRequest = client.getPlayer().getHelpRequest();
    }
}
