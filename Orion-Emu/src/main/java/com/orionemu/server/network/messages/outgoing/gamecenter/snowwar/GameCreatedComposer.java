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

public class GameCreatedComposer  extends MessageComposer{

	private final RoomQueue roomQueue;

	public GameCreatedComposer(RoomQueue roomQueue) {
		this.roomQueue = roomQueue;
	}
	@Override
	public void compose(IComposer msg) {
		SerializeGame2.parse(msg, roomQueue);
	}

	@Override
	public short getId() {
		return 2041;
	}
}
