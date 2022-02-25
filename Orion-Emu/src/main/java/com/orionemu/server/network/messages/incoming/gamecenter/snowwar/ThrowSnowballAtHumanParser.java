package com.orionemu.server.network.messages.incoming.gamecenter.snowwar;


/*
 * ****************
 * @author capos *
 * ****************
 */

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

public class ThrowSnowballAtHumanParser implements Event {
	@Override
	public void handle(Session client, MessageEvent msg) throws Exception {
		int playerId = msg.readInt();
		int type = msg.readInt();
		client.snowWarPlayerData.throwSnowballAtHuman(playerId, type);
	}
}