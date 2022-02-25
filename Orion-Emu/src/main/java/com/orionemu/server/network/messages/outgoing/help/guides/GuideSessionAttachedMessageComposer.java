package com.orionemu.server.network.messages.outgoing.help.guides;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.guides.types.HelpRequest;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class GuideSessionAttachedMessageComposer extends MessageComposer {
    private final HelpRequest helpRequest;

    private final boolean isGuide;

    public GuideSessionAttachedMessageComposer(final HelpRequest helpRequest, final boolean isGuide) {
        this.helpRequest = helpRequest;
        this.isGuide = isGuide;
    }

    @Override
    public short getId() {
        return Composers.GuideSessionAttachedMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(this.isGuide);
        msg.writeInt(1);//type
        msg.writeString(helpRequest.getMessage());
        msg.writeInt(60);//avg waiting time
    }
}