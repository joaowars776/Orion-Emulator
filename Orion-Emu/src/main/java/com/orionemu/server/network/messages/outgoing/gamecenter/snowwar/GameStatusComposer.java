package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.ComposerShit;
import com.orionemu.games.snowwar.MessageWriter;
import com.orionemu.games.snowwar.SnowWarRoom;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGameStatus;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class GameStatusComposer extends MessageComposer {

    public final static MessageWriter compose(SnowWarRoom arena) {
        final MessageWriter ClientMessage = new MessageWriter(100 + (arena.gameEvents.size() * 50));

        ComposerShit.initPacket(1534, ClientMessage);
        SerializeGameStatus.parseNew(ClientMessage, arena, false);
        ComposerShit.endPacket(ClientMessage);

        return ClientMessage;
    }

    private final SnowWarRoom arena;

    public GameStatusComposer(final SnowWarRoom arena) {
        this.arena = arena;
    }

    @Override
    public void compose(IComposer msg) {
        SerializeGameStatus.parse(msg, arena, false);
    }

    @Override
    public short getId() {
        return 1534;
    }
}
