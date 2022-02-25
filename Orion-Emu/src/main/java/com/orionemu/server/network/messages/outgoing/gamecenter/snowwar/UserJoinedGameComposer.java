package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2Player;
import com.orionemu.server.network.sessions.Session;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class UserJoinedGameComposer extends MessageComposer {

	private final Session session;

	public UserJoinedGameComposer(Session session) {
		this.session = session;
	}

	@Override
	public void compose(IComposer msg) {
		SerializeGame2Player.parse(msg, this.session);
		msg.writeBoolean(false); // notused
	}

	@Override
	public short getId() {
		return 956;
	}
}
