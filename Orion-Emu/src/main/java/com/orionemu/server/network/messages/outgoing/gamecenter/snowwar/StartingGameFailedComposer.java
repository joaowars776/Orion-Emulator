package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class StartingGameFailedComposer extends MessageComposer {

	private final int errorCode;
	public StartingGameFailedComposer(int errorCode) {
		this.errorCode = errorCode;
	}
	@Override
	public void compose(IComposer msg) {
		msg.writeInt(this.errorCode);
	}

	@Override
	public short getId() {
		return 0;
	}
}
