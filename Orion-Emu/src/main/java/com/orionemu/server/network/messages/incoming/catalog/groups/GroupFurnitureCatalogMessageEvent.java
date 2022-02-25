package com.orionemu.server.network.messages.incoming.catalog.groups;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.group.GroupDataMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class GroupFurnitureCatalogMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.send(new GroupDataMessageComposer(client.getPlayer().getGroups(), client.getPlayer().getId()));
    }
}
