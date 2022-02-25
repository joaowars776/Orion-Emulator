package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class PlayerRematchesComposer extends MessageComposer{

	private final int playerId;

	public PlayerRematchesComposer(int playerId) {
		this.playerId = playerId;
	}

	@Override
	public void compose(IComposer msg) {
		msg.writeInt(this.playerId);
	}

	@Override
	public short getId() {
		return 0;
	}
}
