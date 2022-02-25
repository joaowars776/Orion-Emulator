package com.orionemu.server.network.messages.outgoing.gamecenter.basejump;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class BaseJumpLeaveQueueComposer extends MessageComposer {
    @Override
    public short getId() {
        return Composers.BaseJumpLeaveQueueComposer;
    }

    @Override
    public void compose(IComposer msg)
    {
        msg.writeInt(3);
    }
}