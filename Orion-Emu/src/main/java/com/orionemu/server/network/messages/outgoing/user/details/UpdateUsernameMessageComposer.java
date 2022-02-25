package com.orionemu.server.network.messages.outgoing.user.details;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;
import com.orionemu.server.storage.queries.player.PlayerDao;

public class UpdateUsernameMessageComposer extends MessageComposer {
    private String user;

    public UpdateUsernameMessageComposer(String user) {
        this.user = user;
    }

    @Override
    public short getId() {
        return Composers.UpdateUsernameMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
    boolean isAvailable = PlayerDao.usernameIsAvailable(user);
        msg.writeInt(isAvailable ? 0 : 5);
        msg.writeString(user);
        msg.writeInt(1);
    }
}
