package com.orionemu.server.network.messages.composers;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.api.networking.messages.IMessageComposer;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.network.messages.protocol.messages.Composer;
import io.netty.buffer.ByteBuf;

public abstract class MessageComposer implements IMessageComposer {
    public MessageComposer() {
    }

    public final IComposer writeMessage(ByteBuf buf) {
        return this.writeMessageImpl(buf);
    }

    public final Composer writeMessageImpl(ByteBuf buffer) {
        final Composer composer = new Composer(this.getId(), buffer);

        // Do anything we need to do with the buffer.

        try {
            this.compose(composer);
        } catch (Exception e) {
            Orion.getServer().getLogger().error("Error composing message " + this.getId() + " / " + this.getClass().getSimpleName(), e);
            throw e;
        } finally {
            this.dispose();
        }

        return composer;
    }

    public abstract short getId();

    public void dispose() {

    }
}
