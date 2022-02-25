package com.orionemu.server.network.messages.outgoing.moderation.tickets;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class HelpTicketResponseMessageComposer extends MessageComposer {

    private int response;

    public HelpTicketResponseMessageComposer(final int response) {
        this.response = response;
    }

    @Override
    public short getId() {
        return Composers.ModeratorSupportTicketResponseMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.response);
        msg.writeString("");
    }
}
