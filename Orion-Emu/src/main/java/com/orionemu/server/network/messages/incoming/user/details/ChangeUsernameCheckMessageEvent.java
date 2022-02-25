package com.orionemu.server.network.messages.incoming.user.details;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.user.details.UpdateUsernameMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.storage.queries.player.PlayerDao;

public class ChangeUsernameCheckMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        String username = msg.readString();

        if (client.getPlayer().getData().getUsername().equals(username)) {
            client.send(new UpdateUsernameMessageComposer(username));
            return;
        }

        boolean isAvailable = PlayerDao.usernameIsAvailable(username);
        client.send(new UpdateUsernameMessageComposer(username));
    }
}
