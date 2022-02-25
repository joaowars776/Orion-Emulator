package com.orionemu.server.network.messages.incoming.gamecenter.snowwar;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.GameDirectoryStatusComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class CheckGameDirectoryStatusParser implements Event {

	@Override
	public void handle(Session client, MessageEvent msg) throws Exception {
		client.send(new GameDirectoryStatusComposer(GameDirectoryStatusComposer.ENABLED));
	}
}
