package com.orionemu.server.network.messages.incoming.navigator.updated;

import com.orionemu.server.game.navigator.types.search.NavigatorSearchService;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

public class NewNavigatorSearchMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        String category = msg.readString();
        String data = msg.readString();

        NavigatorSearchService.getInstance().submitRequest(client.getPlayer(), category, data);
    }
}
