package com.orionemu.server.network.messages.incoming.navigator.updated;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.navigator.updated.NavigatorSavedSearchesMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.storage.queries.player.PlayerDao;

public class DeleteNavigatorSavedSearchMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final int savedSearch = msg.readInt();

        if(client.getPlayer().getNavigator().getSavedSearches().containsKey(savedSearch)) {
            PlayerDao.deleteSearch(savedSearch);
            client.getPlayer().getNavigator().getSavedSearches().remove(savedSearch);

            client.send(new NavigatorSavedSearchesMessageComposer(client.getPlayer().getNavigator().getSavedSearches()));
        }
    }
}
