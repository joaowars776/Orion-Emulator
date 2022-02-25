package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.RoomQueue;
import com.orionemu.games.snowwar.SnowWar;
import com.orionemu.server.network.sessions.Session;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class SerializeGame2 {
    public static void parse(final IComposer msg, final RoomQueue queue) {
        msg.writeInt(queue.room.roomId);
        msg.writeString(queue.room.Name);
        msg.writeInt(0); // notused
        msg.writeInt(queue.room.ArenaType.ArenaType);
        msg.writeInt(SnowWar.TEAMS.length);
        msg.writeInt(SnowWar.MAXPLAYERS);
        msg.writeString(queue.room.Owner);
        msg.writeInt(14); // notused
        msg.writeInt(queue.players.size());
        for (final Session cn : queue.players.values()) {
            SerializeGame2Player.parse(msg, cn);
        }
    }
}
