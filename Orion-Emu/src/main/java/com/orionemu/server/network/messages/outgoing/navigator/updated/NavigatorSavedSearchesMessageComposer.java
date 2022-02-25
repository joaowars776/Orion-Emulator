package com.orionemu.server.network.messages.outgoing.navigator.updated;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.players.components.types.navigator.SavedSearch;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.Map;

public class NavigatorSavedSearchesMessageComposer extends MessageComposer {

    private final Map<Integer, SavedSearch> savedSearches;

    public NavigatorSavedSearchesMessageComposer(final Map<Integer, SavedSearch> savedSearches) {
        this.savedSearches = savedSearches;
    }

    @Override
    public short getId() {
        return Composers.NavigatorSavedSearchesMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.savedSearches.size());//count

        for(Map.Entry<Integer, SavedSearch> savedSearch : this.savedSearches.entrySet()) {
            msg.writeInt(savedSearch.getKey());
            msg.writeString(savedSearch.getValue().getView());
            msg.writeString(savedSearch.getValue().getSearchQuery());
            msg.writeString("");
        }
    }
}
