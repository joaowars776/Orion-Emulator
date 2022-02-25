package com.orionemu.server.network.messages.outgoing.messenger;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.players.data.PlayerAvatar;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.List;


public class FriendRequestsMessageComposer extends MessageComposer {
    private final List<PlayerAvatar> requests;

    public FriendRequestsMessageComposer(final List<PlayerAvatar> requests) {
        this.requests = requests;
    }

    @Override
    public short getId() {
        return Composers.BuddyRequestsMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.requests.size());
        msg.writeInt(this.requests.size());

        for (PlayerAvatar avatar : this.requests) {
            msg.writeInt(avatar.getId());
            msg.writeString(avatar.getUsername());
            msg.writeString(avatar.getFigure());
        }
    }

    @Override
    public void dispose() {
        this.requests.clear();
    }
}
