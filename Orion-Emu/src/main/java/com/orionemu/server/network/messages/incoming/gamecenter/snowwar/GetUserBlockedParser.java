package com.orionemu.server.network.messages.incoming.gamecenter.snowwar;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.UserBlockedComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class GetUserBlockedParser implements Event {
	@Override
	public void handle(Session client, MessageEvent msg) throws Exception {
		client.send(new UserBlockedComposer(0));
	}
}
