package com.orionemu.server.network.messages.outgoing.room.items.lovelock;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class LoveLockConfirmedMessageComposer extends MessageComposer {
    private final int itemId;

    public LoveLockConfirmedMessageComposer(final int itemId) {
        this.itemId = itemId;
    }

    @Override
    public short getId() {
        return Composers.LoveLockDialogueSetLockedMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(itemId);
    }
}
