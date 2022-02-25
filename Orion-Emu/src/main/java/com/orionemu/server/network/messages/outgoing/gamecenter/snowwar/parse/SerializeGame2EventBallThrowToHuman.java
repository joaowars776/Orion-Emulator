package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.ComposerShit;
import com.orionemu.games.snowwar.MessageWriter;
import com.orionemu.games.snowwar.gameevents.BallThrowToHuman;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class SerializeGame2EventBallThrowToHuman {
    public static void parse(final IComposer msg, final BallThrowToHuman evt) {
        msg.writeInt(evt.attacker.objectId);
        msg.writeInt(evt.victim.objectId);
        msg.writeInt(evt.type);
    }

    public static void parse(final MessageWriter ClientMessage, final BallThrowToHuman evt) {
        ComposerShit.add(evt.attacker.objectId, ClientMessage);
        ComposerShit.add(evt.victim.objectId, ClientMessage);
        ComposerShit.add(evt.type, ClientMessage);
    }
}
