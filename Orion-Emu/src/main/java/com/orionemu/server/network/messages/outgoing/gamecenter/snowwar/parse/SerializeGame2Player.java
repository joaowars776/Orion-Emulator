package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.sessions.Session;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class SerializeGame2Player {
    public static void parse(final IComposer msg, final Session cn) {
        msg.writeInt(cn.getPlayer().getData().getId());
        msg.writeString(cn.getPlayer().getData().getUsername());
        msg.writeString(cn.getPlayer().getData().getFigure());
        msg.writeString(cn.getPlayer().getData().getGender().toUpperCase());
        msg.writeInt(cn.snowWarPlayerData.humanObject.team);
        msg.writeInt(cn.getPlayer().getData().getLevel());
        msg.writeInt(cn.snowWarPlayerData.score);
        msg.writeInt(cn.snowWarPlayerData.PointsNeed);
    }
}