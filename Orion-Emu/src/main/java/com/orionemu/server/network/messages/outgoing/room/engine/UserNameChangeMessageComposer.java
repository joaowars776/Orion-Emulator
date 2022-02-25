package com.orionemu.server.network.messages.outgoing.room.engine;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;
import com.orionemu.server.storage.queries.player.PlayerDao;

public class UserNameChangeMessageComposer extends MessageComposer {
    private int roomId;
    private int playerId;
    private String username;

    public UserNameChangeMessageComposer(int roomId, int playerId, String username) {
        this.roomId = roomId;
        this.playerId = playerId;
        this.username = username;
    }

    @Override
    public short getId() {
        return Composers.UserNameChangeMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        boolean isAvailable = PlayerDao.usernameIsAvailable(username);
        msg.writeInt(isAvailable ? 0 : 5);
        msg.writeString(username);
        msg.writeInt(1);
    }
}
