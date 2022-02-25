package com.orionemu.server.network.messages.outgoing.catalog;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.catalog.types.CatalogItem;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class CatalogOfferMessageComposer extends MessageComposer {
    private final CatalogItem catalogItem;

    public CatalogOfferMessageComposer(final CatalogItem catalogItem) {
        this.catalogItem = catalogItem;
    }

    @Override
    public short getId() {
        return Composers.CatalogOfferMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        this.catalogItem.compose(msg);
    }
}
