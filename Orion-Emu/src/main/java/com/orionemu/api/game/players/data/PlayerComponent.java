package com.orionemu.api.game.players.data;


import com.orionemu.api.game.players.BasePlayer;

public interface PlayerComponent {
    BasePlayer getPlayer();

    void dispose();
}
