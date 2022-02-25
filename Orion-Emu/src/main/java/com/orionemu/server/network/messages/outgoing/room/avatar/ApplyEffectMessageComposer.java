package com.orionemu.server.network.messages.outgoing.room.avatar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class ApplyEffectMessageComposer extends MessageComposer {

    private final int avatarId;
    private final int effectId;

    public ApplyEffectMessageComposer(final int avatarId, final int effectId) {
        this.avatarId = avatarId;
        this.effectId = effectId;
    }

    @Override
    public short getId() {
        return Composers.AvatarEffectMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(avatarId);
        msg.writeInt(effectId);
        msg.writeInt(0);
    }
}
