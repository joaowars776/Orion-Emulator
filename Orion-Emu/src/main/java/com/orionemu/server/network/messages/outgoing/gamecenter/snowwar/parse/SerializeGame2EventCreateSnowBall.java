package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.ComposerShit;
import com.orionemu.games.snowwar.MessageWriter;
import com.orionemu.games.snowwar.gameevents.CreateSnowBall;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class SerializeGame2EventCreateSnowBall {
    public static void parse(final IComposer msg, final CreateSnowBall evt) {
        msg.writeInt(evt.ball.objectId);
        msg.writeInt(evt.player.objectId);
        msg.writeInt(evt.x);
        msg.writeInt(evt.y);
        msg.writeInt(evt.type);
    }

    public static void parse(final MessageWriter ClientMessage, final CreateSnowBall evt) {
        ComposerShit.add(evt.ball.objectId, ClientMessage);
        ComposerShit.add(evt.player.objectId, ClientMessage);
        ComposerShit.add(evt.x, ClientMessage);
        ComposerShit.add(evt.y, ClientMessage);
        ComposerShit.add(evt.type, ClientMessage);
    }
}