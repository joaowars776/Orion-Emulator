package com.orionemu.server.network.messages.incoming.messenger;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class SearchFriendsMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        String query = msg.readString();

        client.send(client.getPlayer().getMessenger().search(query));
    }
}
