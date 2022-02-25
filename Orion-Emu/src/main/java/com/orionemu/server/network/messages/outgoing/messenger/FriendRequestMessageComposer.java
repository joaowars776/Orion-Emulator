package com.orionemu.server.network.messages.outgoing.messenger;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.players.data.PlayerAvatar;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class FriendRequestMessageComposer extends MessageComposer {
    private final PlayerAvatar playerAvatar;

    public FriendRequestMessageComposer(final PlayerAvatar playerAvatar) {
        this.playerAvatar = playerAvatar;
    }

    @Override
    public short getId() {
        return Composers.NewBuddyRequestMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.playerAvatar.getId());
        msg.writeString(this.playerAvatar.getUsername());
        msg.writeString(this.playerAvatar.getFigure());
    }
}
