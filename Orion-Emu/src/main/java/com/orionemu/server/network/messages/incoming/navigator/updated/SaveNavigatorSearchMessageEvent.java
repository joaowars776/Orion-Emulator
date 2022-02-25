package com.orionemu.server.network.messages.incoming.navigator.updated;

import com.orionemu.server.game.players.components.types.navigator.SavedSearch;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.navigator.updated.NavigatorSavedSearchesMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.storage.queries.player.PlayerDao;

public class SaveNavigatorSearchMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final String view = msg.readString();
        final String searchQuery = msg.readString();

        final SavedSearch savedSearch = new SavedSearch(view, searchQuery);

        if(!client.getPlayer().getNavigator().isSearchSaved(savedSearch)) {
            // Save the search
            final int searchId = PlayerDao.saveSearch(client.getPlayer().getId(), savedSearch);

            client.getPlayer().getNavigator().getSavedSearches().put(searchId, savedSearch);
            client.send(new NavigatorSavedSearchesMessageComposer(client.getPlayer().getNavigator().getSavedSearches()));
        }
    }
}
