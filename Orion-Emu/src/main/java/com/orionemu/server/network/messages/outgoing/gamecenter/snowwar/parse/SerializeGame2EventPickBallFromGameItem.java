package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.ComposerShit;
import com.orionemu.games.snowwar.MessageWriter;
import com.orionemu.games.snowwar.gameevents.PickBallFromGameItem;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class SerializeGame2EventPickBallFromGameItem {
    public static void parse(final IComposer msg, final PickBallFromGameItem evt) {
        msg.writeInt(evt.player.objectId);
        msg.writeInt(evt.gameItem.objectId);
    }

    public static void parse(final MessageWriter ClientMessage, final PickBallFromGameItem evt) {
        ComposerShit.add(evt.player.objectId, ClientMessage);
        ComposerShit.add(evt.gameItem.objectId, ClientMessage);
    }
}
