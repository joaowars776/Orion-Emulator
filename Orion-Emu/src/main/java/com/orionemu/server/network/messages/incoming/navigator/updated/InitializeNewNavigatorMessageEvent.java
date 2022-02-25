package com.orionemu.server.network.messages.incoming.navigator.updated;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.navigator.NavigatorMetaDataMessageComposer;
import com.orionemu.server.network.messages.outgoing.navigator.updated.NavigatorCollapsedCategoriesMessageComposer;
import com.orionemu.server.network.messages.outgoing.navigator.updated.NavigatorLiftedRoomsMessageComposer;
import com.orionemu.server.network.messages.outgoing.navigator.updated.NavigatorPreferencesMessageComposer;
import com.orionemu.server.network.messages.outgoing.navigator.updated.NavigatorSavedSearchesMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

public class InitializeNewNavigatorMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.sendQueue(new NavigatorPreferencesMessageComposer(client.getPlayer().getSettings()))
                .sendQueue(new NavigatorMetaDataMessageComposer())
                .sendQueue(new NavigatorLiftedRoomsMessageComposer())
                .sendQueue(new NavigatorSavedSearchesMessageComposer(client.getPlayer().getNavigator().getSavedSearches()))
                .sendQueue(new NavigatorCollapsedCategoriesMessageComposer());

        client.flush();
    }
}
