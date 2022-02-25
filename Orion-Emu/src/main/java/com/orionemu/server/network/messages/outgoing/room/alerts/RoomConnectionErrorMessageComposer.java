package com.orionemu.server.network.messages.outgoing.room.alerts;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class RoomConnectionErrorMessageComposer extends MessageComposer {
    private final int errorCode;
    private final String extras;

    public RoomConnectionErrorMessageComposer(final int errorCode, final String extras) {
        this.errorCode = errorCode;
        this.extras = extras;
    }

    @Override
    public short getId() {
        return Composers.RoomErrorNotifMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(errorCode);

        if (!extras.isEmpty())
            msg.writeString(extras);

    }
}
