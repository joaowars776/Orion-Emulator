package com.orionemu.server.network.messages.outgoing.user.buildersclub;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class BuildersClubMembershipMessageComposer extends MessageComposer {
    @Override
    public short getId() {
        return Composers.BuildersClubMembershipMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(999999999);
        msg.writeInt(100);
        msg.writeInt(2);
    }
}
