package com.orionemu.server.network.messages.outgoing.handshake;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

public class ConfirmUsernameMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final String username = msg.readString();

        if(client.getPlayer() != null) {
            if(client.getPlayer().getData().getUsername().equals(username)) {
                client.getPlayer().setUsernameConfirmed(true);
            }
        }
    }

}