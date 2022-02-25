package com.orionemu.server.network.messages.outgoing.room.items.wired;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class WiredRewardMessageComposer extends MessageComposer {
    private final int reason;

    public WiredRewardMessageComposer(final int reason) {
        this.reason = reason;
    }

    @Override
    public short getId() {
        return Composers.WiredRewardMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        // 1-5 = error
        // 6-7 = success (rewardMisc, rewardBadge)
        msg.writeInt(reason);
    }
}