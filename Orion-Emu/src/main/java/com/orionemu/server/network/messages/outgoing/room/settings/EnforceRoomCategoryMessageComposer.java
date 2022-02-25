package com.orionemu.server.network.messages.outgoing.room.settings;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class EnforceRoomCategoryMessageComposer extends MessageComposer {

    private int defaultCategory = 16;

    public EnforceRoomCategoryMessageComposer() {

    }

    @Override
    public short getId() {
        return Composers.EnforceCategoryUpdateMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(defaultCategory);
    }
}
