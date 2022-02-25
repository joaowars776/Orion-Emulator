package com.orionemu.server.network.messages.incoming.help;

import com.orionemu.server.game.moderation.ModerationManager;
import com.orionemu.server.game.moderation.types.tickets.HelpTicket;
import com.orionemu.server.game.moderation.types.tickets.HelpTicketState;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.help.InitHelpToolMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

public class DeletePendingTicketMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final HelpTicket helpTicket = ModerationManager.getInstance().getActiveTicketByPlayerId(client.getPlayer().getId());

        if (helpTicket != null) {
            helpTicket.setState(HelpTicketState.CLOSED);
            helpTicket.save();

            ModerationManager.getInstance().broadcastTicket(helpTicket);

            ModerationManager.getInstance().getTickets().remove(helpTicket.getId());
            client.send(new InitHelpToolMessageComposer(null));
        }
    }
}
