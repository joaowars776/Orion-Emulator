package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class GameChatFromPlayerComposer extends MessageComposer {

	private final int userId;
	private final String text;

	public GameChatFromPlayerComposer(final int userId, final String text) {
		this.userId = userId;
		this.text = text;
	}

	@Override
	public void compose(IComposer msg) {
		msg.writeInt(this.userId);
		msg.writeString(this.text);
	}

	@Override
	public short getId() {
		return 692;
	}
}
