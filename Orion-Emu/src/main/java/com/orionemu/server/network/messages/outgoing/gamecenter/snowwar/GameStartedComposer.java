package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.RoomQueue;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class GameStartedComposer extends MessageComposer{

	private final RoomQueue queue;

	public static int HEADER;

	public GameStartedComposer(RoomQueue queue) {
		this.queue = queue;
	}

	@Override
	public void compose(IComposer msg) {
		SerializeGame2.parse(msg, queue);
	}

	@Override
	public short getId() {
		return 3783;
	}
}
