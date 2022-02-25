package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.ComposerShit;
import com.orionemu.games.snowwar.MessageWriter;
import com.orionemu.games.snowwar.gameevents.UserMove;
import io.netty.buffer.ByteBuf;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class SerializeGame2EventMove {
    public static void parse(final IComposer msg, final UserMove evt) {
        msg.writeInt(evt.player.objectId);
        msg.writeInt(evt.x);
        msg.writeInt(evt.y);
    }

    public static void parse(final MessageWriter ClientMessage, final UserMove evt) {
        ComposerShit.add(evt.player.objectId, ClientMessage);
        ComposerShit.add(evt.x, ClientMessage);
        ComposerShit.add(evt.y, ClientMessage);
    }
}
