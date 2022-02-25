package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class UserLeftGameComposer extends MessageComposer {

	private final int playerId;

	public UserLeftGameComposer(int playerId) {
		this.playerId = playerId;
	}

	@Override
	public void compose(IComposer msg) {
		msg.writeInt(this.playerId);
	}

	@Override
	public short getId() {
		return 2438;
	}
}
