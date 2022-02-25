package com.orionemu.api.networking.sessions;

import com.orionemu.api.game.players.BasePlayer;
import com.orionemu.api.networking.messages.IMessageComposer;

public interface BaseSession {
    BasePlayer getPlayer();

    void disconnect();

    BaseSession send(IMessageComposer messageComposer);

    BaseSession sendQueue(IMessageComposer messageComposer);

    String getIpAddress();
}
