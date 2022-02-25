package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.ComposerShit;
import com.orionemu.games.snowwar.MessageWriter;
import com.orionemu.games.snowwar.gameevents.BallThrowToPosition;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class SerializeGame2EventBallThrowToPosition {
    public static void parse(final IComposer msg, final BallThrowToPosition evt) {
        msg.writeInt(evt.attacker.objectId);
        msg.writeInt(evt.x);
        msg.writeInt(evt.y);
        msg.writeInt(evt.type);
    }

    public static void parse(final MessageWriter ClientMessage, final BallThrowToPosition evt) {
        ComposerShit.add(evt.attacker.objectId, ClientMessage);
        ComposerShit.add(evt.x, ClientMessage);
        ComposerShit.add(evt.y, ClientMessage);
        ComposerShit.add(evt.type, ClientMessage);
    }
}
