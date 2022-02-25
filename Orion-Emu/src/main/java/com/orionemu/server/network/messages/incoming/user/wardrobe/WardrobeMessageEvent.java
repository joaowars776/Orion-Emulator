package com.orionemu.server.network.messages.incoming.user.wardrobe;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.user.wardrobe.WardrobeMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class WardrobeMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.send(new WardrobeMessageComposer(client.getPlayer().getSettings().getWardrobe()));
    }
}
