package com.orionemu.server.network.messages.outgoing.room.trading;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class TradeConfirmationMessageComposer extends MessageComposer {

    @Override
    public short getId() {
        return Composers.TradingCompleteMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {

    }
}
