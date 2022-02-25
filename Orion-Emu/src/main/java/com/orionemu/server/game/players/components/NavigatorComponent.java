package com.orionemu.server.game.players.components;

import com.orionemu.server.game.players.components.types.navigator.SavedSearch;
import com.orionemu.server.game.players.types.Player;
import com.orionemu.server.storage.queries.player.PlayerDao;

import java.util.Map;

public class NavigatorComponent {
    private final Player player;

    private final Map<Integer, SavedSearch> savedSearches;

    public NavigatorComponent(final Player player) {
        this.player = player;

        this.savedSearches = PlayerDao.getSavedSearches(this.player.getId());
    }

    public void dispose() {
        this.savedSearches.clear();
    }

    public boolean isSearchSaved(SavedSearch newSearch) {
        for(SavedSearch savedSearch : this.getSavedSearches().values()) {
            if(savedSearch.getView().equals(newSearch.getView()) && savedSearch.getSearchQuery().equals(newSearch.getSearchQuery()))
                return true;
        }

        return false;
    }

    public Map<Integer, SavedSearch> getSavedSearches() {
        return this.savedSearches;
    }
}
