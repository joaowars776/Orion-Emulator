package com.orionemu.server.network.messages.incoming.gamecenter.snowwar;

import com.orionemu.games.snowwar.SnowWarRoom;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class RequestFullStatusUpdateParser implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final SnowWarRoom room = client.snowWarPlayerData.currentSnowWar;
        if (room == null) {
            return;
        }

        room.fullGameStatusQueue.add(client.getChannel());
    }
}