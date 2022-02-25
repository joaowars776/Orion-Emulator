package com.orionemu.server.network.messages.incoming.user.club;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.user.club.ClubStatusMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class ClubStatusMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        if (client == null) {
            return;
        }
        
        client.send(new ClubStatusMessageComposer(client.getPlayer().getSubscription()));
        client.send(client.getPlayer().composeCurrenciesBalance());
    }
}
