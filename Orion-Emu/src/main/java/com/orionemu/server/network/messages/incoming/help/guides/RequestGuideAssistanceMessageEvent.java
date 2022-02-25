package com.orionemu.server.network.messages.incoming.help.guides;

import com.orionemu.server.game.guides.GuideManager;
import com.orionemu.server.game.guides.types.HelpRequest;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.help.guides.GuideSessionErrorMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

public class RequestGuideAssistanceMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final int type = msg.readInt();
        final String message = msg.readString();

        final HelpRequest existingHelpRequest = GuideManager.getInstance().getHelpRequestByCreator(client.getPlayer().getId());

        if(existingHelpRequest != null) {
            // Error to the user.
            client.send(new GuideSessionErrorMessageComposer(GuideSessionErrorMessageComposer.SOMETHING_WRONG_REQUEST));
            System.out.println("här");
            return;
        }

        if(GuideManager.getInstance().getActiveGuideCount() == 0) {
            client.send(new GuideSessionErrorMessageComposer(GuideSessionErrorMessageComposer.NO_GUARDIANS_AVAILABLE));
            System.out.println("där");
            return;
        }

        final HelpRequest helpRequest = new HelpRequest(client.getPlayer().getId(), type, message);

        GuideManager.getInstance().requestHelp(helpRequest);
    }
}
