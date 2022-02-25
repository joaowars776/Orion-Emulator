package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class AccountGameStatusComposer extends MessageComposer {

	private final int gameTypeId;

	public AccountGameStatusComposer(final int gameTypeId) {
		this.gameTypeId = gameTypeId;
	}

	@Override
	public void compose(IComposer msg) {
		msg.writeInt(gameTypeId);
		msg.writeInt(-1); // unlimited
		msg.writeInt(0); // 0=free?
	}

	@Override
	public short getId() {
		return 0;
	}
}
