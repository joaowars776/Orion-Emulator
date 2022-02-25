package com.orionemu.server.network.messages.outgoing.notification;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;


public class LogoutMessageComposer extends MessageComposer {
    private final int reason;

    public LogoutMessageComposer(final String reason) {
        if (reason.equals("banned")) {
            this.reason = 1;
        } else {
            this.reason = 0;
        }
    }

    @Override
    public short getId() {
        return 0;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.reason);
    }
}
