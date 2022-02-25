package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class GameCancelledComposer extends MessageComposer {
	@Override
	public void compose(IComposer msg) {

	}

	@Override
	public short getId() {
		return 0;
	}
}
