package com.orionemu.server.network.messages.incoming.messenger;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.messenger.FriendRequestsMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

public class FriendRequestsMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.send(new FriendRequestsMessageComposer(client.getPlayer().getMessenger().getRequestAvatars()));
    }
}
