package com.orionemu.server.network.messages.outgoing.help.guides;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.guides.GuideManager;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class GuideToolsMessageComposer extends MessageComposer {

    private final boolean onDuty;

    public GuideToolsMessageComposer(final boolean onDuty) {
        this.onDuty = onDuty;
    }

    @Override
    public short getId() {
        return Composers.GuideToolsMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(this.onDuty);
        msg.writeInt(0);
        msg.writeInt(GuideManager.getInstance().getActiveGuideCount());//active guides
        msg.writeInt(GuideManager.getInstance().getActiveGuardianCount());//active guardians
    }
}