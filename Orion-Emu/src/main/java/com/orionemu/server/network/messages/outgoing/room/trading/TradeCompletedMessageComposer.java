package com.orionemu.server.network.messages.outgoing.room.trading;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class TradeCompletedMessageComposer extends MessageComposer {
    @Override
    public short getId() {
        return Composers.TradingFinishMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {

    }
}
