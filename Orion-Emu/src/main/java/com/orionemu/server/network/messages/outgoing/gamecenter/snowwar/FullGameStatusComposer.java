package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.SnowWar;
import com.orionemu.games.snowwar.SnowWarRoom;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2GameObjects;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGameStatus;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class FullGameStatusComposer extends MessageComposer {

    private final SnowWarRoom arena;

    public FullGameStatusComposer(final SnowWarRoom arena) {
        this.arena = arena;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(0); // not used
        msg.writeInt(0); // not used
        msg.writeInt(0); // not used
        SerializeGame2GameObjects.parse(msg, arena);
        msg.writeInt(0); // not used
        msg.writeInt(SnowWar.TEAMS.length);
        SerializeGameStatus.parse(msg, arena, true);
    }

    @Override
    public short getId() {
        return 1216;
    }
}
