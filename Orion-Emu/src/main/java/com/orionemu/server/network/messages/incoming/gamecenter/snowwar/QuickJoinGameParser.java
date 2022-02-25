package com.orionemu.server.network.messages.incoming.gamecenter.snowwar;


/*
 * ****************
 * @author capos *
 * ****************
 */

import com.orionemu.games.snowwar.SnowPlayerQueue;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

public class QuickJoinGameParser implements Event {
	@Override
	public void handle(Session client, MessageEvent msg) throws Exception {
		SnowPlayerQueue.addPlayerInQueue(client);
	}
}