package com.orionemu.server.network.messages.outgoing.navigator.updated;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class NavigatorLiftedRoomsMessageComposer extends MessageComposer {
    @Override
    public short getId() {
        return Composers.NavigatorLiftedRoomsMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(0);
    }
}
