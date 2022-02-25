package com.orionemu.api.game.players.data.components;

import com.orionemu.api.game.players.data.PlayerComponent;
import com.orionemu.api.game.players.data.components.permissions.PlayerRank;

public interface PlayerPermissions extends PlayerComponent {
    PlayerRank getRank();

    boolean hasCommand(String key);
}
