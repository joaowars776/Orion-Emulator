package com.orionemu.server.network.messages.outgoing.help;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.moderation.types.tickets.HelpTicket;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class InitHelpToolMessageComposer extends MessageComposer {
    private final HelpTicket helpTicket;

    public InitHelpToolMessageComposer(HelpTicket helpTicket) {
        this.helpTicket = helpTicket;
    }

    @Override
    public short getId() {
        return Composers.CallForHelpPendingCallsMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.helpTicket == null ? 0 : 1);

        if (this.helpTicket != null) {
            msg.writeString(this.helpTicket.getId());
            msg.writeString(this.helpTicket.getDateSubmitted());
            msg.writeString(this.helpTicket.getMessage());
        }
    }
}