package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.ComposerShit;
import com.orionemu.games.snowwar.MessageWriter;
import com.orionemu.games.snowwar.gameevents.PlayerLeft;
import io.netty.buffer.ByteBuf;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class SerializeGame2EventPlayerLeft {
    public static void parse(final IComposer msg, final PlayerLeft evt) {
        msg.writeInt(evt.player.objectId);
    }

    public static void parse(final MessageWriter ClientMessage, final PlayerLeft evt) {
        ComposerShit.add(evt.player.objectId, ClientMessage);
    }
}
