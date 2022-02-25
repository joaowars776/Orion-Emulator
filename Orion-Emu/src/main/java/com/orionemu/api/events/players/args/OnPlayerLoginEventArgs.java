package com.orionemu.api.events.players.args;

import com.orionemu.api.events.EventArgs;
import com.orionemu.api.game.players.BasePlayer;

public class OnPlayerLoginEventArgs extends EventArgs {
    private BasePlayer player;

    public OnPlayerLoginEventArgs(BasePlayer player) {
        this.player = player;
    }

    public BasePlayer getPlayer() {
        return this.player;
    }
}
