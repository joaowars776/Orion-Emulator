package com.orionemu.server.network.messages.outgoing.user.details;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class AvatarAspectUpdateMessageComposer extends MessageComposer {

    private final String figure;
    private final String gender;

    public AvatarAspectUpdateMessageComposer(String figure, String gender) {
        this.figure = figure;
        this.gender = gender;
    }

    @Override
    public short getId() {
        return Composers.AvatarAspectUpdateMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.figure);
        msg.writeString(this.gender);
    }
}
