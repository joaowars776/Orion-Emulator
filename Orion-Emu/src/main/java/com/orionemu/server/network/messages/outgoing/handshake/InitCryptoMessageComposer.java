package com.orionemu.server.network.messages.outgoing.handshake;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class InitCryptoMessageComposer extends MessageComposer {
    private final String key;
    private final String flag;

    public InitCryptoMessageComposer(final String key, String flag) {
        this.key = key;
        this.flag = flag;
    }

    @Override
    public short getId() {
        return Composers.InitCryptoMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.key);
        msg.writeString(this.flag);
    }
}
