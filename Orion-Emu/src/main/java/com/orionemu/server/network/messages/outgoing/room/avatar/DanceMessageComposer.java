package com.orionemu.server.network.messages.outgoing.room.avatar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class DanceMessageComposer extends MessageComposer {
    private final int playerId;
    private final int danceId;

    public DanceMessageComposer(final int playerId, final int danceId) {
        this.playerId = playerId;
        this.danceId = danceId;
    }

    @Override
    public short getId() {
        return Composers.DanceMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(playerId);
        msg.writeInt(danceId);
    }
}
