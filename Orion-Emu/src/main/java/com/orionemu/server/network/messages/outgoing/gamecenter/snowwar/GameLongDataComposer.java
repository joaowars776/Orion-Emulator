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

public class GameLongDataComposer extends MessageComposer{

	private final RoomQueue lobby;

	public GameLongDataComposer(RoomQueue lobby) {
		this.lobby = lobby;
	}

	@Override
	public void compose(IComposer msg) {
		SerializeGame2.parse(msg, lobby);
	}

	@Override
	public short getId() {
		return 1707;
	}
}
