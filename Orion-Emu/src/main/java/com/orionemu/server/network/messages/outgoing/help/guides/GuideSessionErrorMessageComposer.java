package com.orionemu.server.network.messages.outgoing.help.guides;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class GuideSessionErrorMessageComposer extends MessageComposer {
    public static final int SOMETHING_WRONG_REQUEST = 0;
    public static final int NO_HELPERS_AVAILABLE = 1;
    public static final int NO_GUARDIANS_AVAILABLE = 2;

    private final int errorCode;

    public GuideSessionErrorMessageComposer(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public short getId() {
        return Composers.GuideSessionErrorMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.errorCode);
    }
}