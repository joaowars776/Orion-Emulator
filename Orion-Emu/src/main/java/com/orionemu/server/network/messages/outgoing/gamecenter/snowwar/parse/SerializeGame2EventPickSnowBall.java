package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.ComposerShit;
import com.orionemu.games.snowwar.MessageWriter;
import com.orionemu.games.snowwar.gameevents.MakeSnowBall;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class SerializeGame2EventPickSnowBall {
    public static void parse(final IComposer msg, final MakeSnowBall evt) {
        msg.writeInt(evt.player.objectId);
    }

    public static void parse(final MessageWriter ClientMessage, final MakeSnowBall evt) {
        ComposerShit.add(evt.player.objectId, ClientMessage);
    }
}