package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.gameobjects.HumanGameObject;

import java.util.Collection;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class SerializeGame2TeamScoreData {
    public static void parse(final IComposer msg, final int TeamId, final int TeamScore, final Collection<HumanGameObject> Players) {
        msg.writeInt(TeamId);
        msg.writeInt(TeamScore);
        msg.writeInt(Players.size());
        for (final HumanGameObject Player : Players) {
            SerializeGame2TeamPlayerData.parse(msg, Player);
        }
    }
}
