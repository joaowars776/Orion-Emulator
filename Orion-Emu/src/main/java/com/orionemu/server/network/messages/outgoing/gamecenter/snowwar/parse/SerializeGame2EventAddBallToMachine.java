package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.ComposerShit;
import com.orionemu.games.snowwar.MessageWriter;
import com.orionemu.games.snowwar.gameevents.AddBallToMachine;
import io.netty.buffer.ByteBuf;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class SerializeGame2EventAddBallToMachine {
    public static void parse(final IComposer msg, final AddBallToMachine evt) {
        msg.writeInt(evt.gameItem.objectId);
    }

    public static void parse(final MessageWriter ClientMessage, final AddBallToMachine evt) {
        ComposerShit.add(evt.gameItem.objectId, ClientMessage);
    }
}
