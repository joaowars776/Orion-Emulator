package com.orionemu.server.network.messages.outgoing.moderation.tickets;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.moderation.types.tickets.HelpTicket;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class HelpTicketMessageComposer extends MessageComposer {
    private final HelpTicket helpTicket;

    public HelpTicketMessageComposer(HelpTicket helpTicket) {
        this.helpTicket = helpTicket;
        ;
    }

    @Override
    public short getId() {
        return Composers.ModeratorSupportTicketMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        this.helpTicket.compose(msg);
    }
}
