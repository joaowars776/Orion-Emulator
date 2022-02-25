package com.orionemu.api.game.furniture.types;

import com.orionemu.api.game.players.data.components.inventory.PlayerItemSnapshot;

public interface SongItem {

    int getSongId();

    PlayerItemSnapshot getItemSnapshot();
}
