package com.orionemu.server.network.messages.outgoing.user.purse;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class UpdateActivityPointsMessageComposer extends MessageComposer {

    private int activityPoints;
    private int change;

    public UpdateActivityPointsMessageComposer(int activityPoints, int change) {
        this.activityPoints = activityPoints;
        this.change = change;
    }

    @Override
    public short getId() {
        return Composers.HabboActivityPointNotificationMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.activityPoints);
        msg.writeInt(this.change);
        msg.writeInt(0);
    }
}
