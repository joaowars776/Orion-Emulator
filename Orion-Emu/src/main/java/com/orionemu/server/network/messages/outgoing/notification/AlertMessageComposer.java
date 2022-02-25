package com.orionemu.server.network.messages.outgoing.notification;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class AlertMessageComposer extends MessageComposer {
    private final String message;
    private final String link;

    public AlertMessageComposer(final String message, final String link) {
        this.message = message;
        this.link = link;
    }

    public AlertMessageComposer(final String message) {
        this(message, "");
    }

    @Override
    public short getId() {
        return Composers.BroadcastMessageAlertMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.message);
        msg.writeString(this.link);
    }
}
