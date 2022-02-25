package com.orionemu.server.network.messages.incoming.gamecenter.snowwar;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class SetUserMoveTargetParser implements Event {

	@Override
	public void handle(Session client, MessageEvent msg) throws Exception {
		if (client.snowWarPlayerData.currentSnowWar == null) {
			return;
		}

		final int x = msg.readInt();
		final int y = msg.readInt();

		msg.readInt(); // Turn
		msg.readInt(); // SubTurn

		if(client.snowWarPlayerData.humanObject.canWalkTo(x, y)) {
			client.snowWarPlayerData.playerMove(x, y);
		}
	}
}