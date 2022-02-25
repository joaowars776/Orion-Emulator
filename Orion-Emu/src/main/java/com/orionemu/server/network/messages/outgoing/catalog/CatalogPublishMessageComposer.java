package com.orionemu.server.network.messages.outgoing.catalog;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class CatalogPublishMessageComposer extends MessageComposer {

    private final boolean showNotification;

    public CatalogPublishMessageComposer(final boolean showNotification) {
        this.showNotification = showNotification;
    }

    @Override
    public short getId() {
        return Composers.CatalogUpdatedMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(this.showNotification);
    }
}
