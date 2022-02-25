package com.orionemu.server.network.messages.incoming.catalog.groups;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.catalog.groups.GroupElementsMessageComposer;
import com.orionemu.server.network.messages.outgoing.catalog.groups.GroupPartsMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class BuyGroupDialogMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        client.send(new GroupPartsMessageComposer(client.getPlayer().getRooms()));
        client.send(new GroupElementsMessageComposer());
    }
}
