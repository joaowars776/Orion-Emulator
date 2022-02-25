package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class UserBlockedComposer extends MessageComposer {

	private final int snowWarBlockedGame;

	public UserBlockedComposer(int snowWarBlockedGame) {
		this.snowWarBlockedGame = snowWarBlockedGame;
	}

	@Override
	public void compose(IComposer msg) {
		msg.writeInt(this.snowWarBlockedGame);
	}

	@Override
	public short getId() {
		return 1295;
	}
}
