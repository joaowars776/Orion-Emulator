package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.SnowWarRoom;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class SerializeGame2SnowWarGameStats {
    public static void parse(final IComposer msg, final SnowWarRoom arena) {
       msg.writeInt(arena.MostKills.userId);
       msg.writeInt(arena.MostHits.userId);
    }
}
