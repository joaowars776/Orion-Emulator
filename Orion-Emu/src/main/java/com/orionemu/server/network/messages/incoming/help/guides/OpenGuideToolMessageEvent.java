package com.orionemu.server.network.messages.incoming.help.guides;

import com.orionemu.server.game.guides.GuideManager;
import com.orionemu.server.game.guides.types.HelperSession;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.help.guides.GuideToolsMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

public class OpenGuideToolMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final boolean onDuty = msg.readBoolean();

        final boolean handleTourRequests = msg.readBoolean();
        final boolean handleHelpRequests = msg.readBoolean();
        final boolean handleBullyRequests = msg.readBoolean();

        if(!onDuty && client.getPlayer().getHelperSession() != null) {
            GuideManager.getInstance().finishPlayerDuty(client.getPlayer().getHelperSession());
            client.getPlayer().setHelperSession(null);
        } else if(onDuty) {
            final HelperSession helperSession = new HelperSession(client.getPlayer().getId(), handleTourRequests, handleHelpRequests, handleBullyRequests);

            client.getPlayer().setHelperSession(helperSession);
            GuideManager.getInstance().startPlayerDuty(helperSession);
        }

        client.send(new GuideToolsMessageComposer(onDuty));
    }
}
