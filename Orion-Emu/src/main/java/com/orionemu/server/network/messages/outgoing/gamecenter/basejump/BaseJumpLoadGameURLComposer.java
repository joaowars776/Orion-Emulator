package com.orionemu.server.network.messages.outgoing.gamecenter.basejump;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.network.messages.composers.MessageComposer;

public class BaseJumpLoadGameURLComposer extends MessageComposer {
    @Override
    public short getId() {
        return 0;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(3);
        msg.writeString(Orion.getServer().getConfig().get("fastfood.hotel.port"));
        msg.writeString(Orion.getServer().getConfig().get("fastfood.hotel.ip"));
    }
}