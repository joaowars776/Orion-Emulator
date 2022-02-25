package com.orionemu.server.network.messages.outgoing.gamecenter;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/**
 * Created by SpreedBlood on 2017-12-23.
 */
public class YouArePlayingGameComposer extends MessageComposer {

    private final boolean isPlaying;

    public YouArePlayingGameComposer(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(this.isPlaying);
    }

    @Override
    public short getId() {
        return 545;
    }
}
