package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.SnowWarRoom;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class SerializeGame2GameResult {
    public static void parse(final IComposer msg, final SnowWarRoom arena) {
        msg.writeBoolean(true);
        msg.writeInt(arena.Result);
        msg.writeInt(arena.Winner);
    }
}