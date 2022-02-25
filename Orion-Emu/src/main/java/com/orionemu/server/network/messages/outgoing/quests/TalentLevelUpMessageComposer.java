package com.orionemu.server.network.messages.outgoing.quests;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

/**
 * Created by Emrik on 2017-06-23.
 */
public class TalentLevelUpMessageComposer extends MessageComposer {
    @Override
    public short getId() {
        return Composers.TalentLevelUpMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {

    }
}
