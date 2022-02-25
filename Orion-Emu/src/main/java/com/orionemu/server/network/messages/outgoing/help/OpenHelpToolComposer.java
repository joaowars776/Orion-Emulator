package com.orionemu.server.network.messages.outgoing.help;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/**
 * Created by SpreedBlood on 2017-12-05.
 */
public class OpenHelpToolComposer extends MessageComposer {
    @Override
    public void compose(IComposer msg) {

    }

    @Override
    public short getId() {
        return 0;
    }
}
