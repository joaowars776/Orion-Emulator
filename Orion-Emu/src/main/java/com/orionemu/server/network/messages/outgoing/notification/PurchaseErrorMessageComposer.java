package com.orionemu.server.network.messages.outgoing.notification;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class PurchaseErrorMessageComposer extends MessageComposer {

    private final int ErrorCode;

    public PurchaseErrorMessageComposer(final int ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    @Override
    public short getId() {
        return Composers.PurchaseErrorMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(ErrorCode);
    }
}
