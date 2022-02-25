package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.SnowWarRoom;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2GameObjects;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class StageStartingComposer extends MessageComposer {

	private final SnowWarRoom arena;

	public StageStartingComposer(SnowWarRoom arena) {
		this.arena = arena;
	}

	@Override
	public void compose(IComposer msg) {
		msg.writeInt(0);
		msg.writeString("snowwar_arena_0");
		msg.writeInt(5);
		SerializeGame2GameObjects.parse(msg, arena);
	}

	@Override
	public short getId() {
		return 540;
	}
}
