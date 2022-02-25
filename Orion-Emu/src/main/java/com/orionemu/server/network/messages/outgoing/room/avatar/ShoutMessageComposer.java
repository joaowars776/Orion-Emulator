package com.orionemu.server.network.messages.outgoing.room.avatar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.rooms.types.misc.ChatEmotion;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class ShoutMessageComposer extends MessageComposer {
    private final int playerId;
    private final String message;
    private final ChatEmotion emoticon;
    private final int colour;

    public ShoutMessageComposer(final int playerId, final String message, final ChatEmotion emoticion, final int colour) {
        this.playerId = playerId;
        this.message = message;
        this.emoticon = emoticion;
        this.colour = colour;
    }

    @Override
    public short getId() {
        return Composers.ShoutMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(playerId);
        msg.writeString(message);
        msg.writeInt(0);
        msg.writeInt(colour);
        msg.writeInt(0);
        msg.writeInt(-1);
    }
}
