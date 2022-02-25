package com.orionemu.server.network.messages.outgoing.notification;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class MotdNotificationMessageComposer extends MessageComposer {
    private final String message;

    public MotdNotificationMessageComposer(final String message) {
        this.message = message;
    }

    public MotdNotificationMessageComposer() {
        this(OrionSettings.motdMessage);
    }

    @Override
    public short getId() {
        return Composers.MOTDNotificationMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(1);
        msg.writeString(message);
    }
}
