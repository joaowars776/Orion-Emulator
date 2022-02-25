package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class StartCounterComposer extends MessageComposer {

	private final int time;

	public StartCounterComposer(int time) {
		this.time = time;
	}

	@Override
	public void compose(IComposer msg) {
		msg.writeInt(time);
	}

	@Override
	public short getId() {
		return 2375;
	}
}
