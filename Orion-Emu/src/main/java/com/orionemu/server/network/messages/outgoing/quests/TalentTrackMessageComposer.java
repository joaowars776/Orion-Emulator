package com.orionemu.server.network.messages.outgoing.quests;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;
/**
 * Created by Emrik on 2017-06-23.
 */
public class TalentTrackMessageComposer extends MessageComposer {
    @Override
    public short getId() {
        return Composers.TalentTrackMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {

    }
}
