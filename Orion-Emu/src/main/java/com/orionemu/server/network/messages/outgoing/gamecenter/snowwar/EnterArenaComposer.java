package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.SnowWar;
import com.orionemu.games.snowwar.SnowWarRoom;
import com.orionemu.games.snowwar.gameobjects.HumanGameObject;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeArena;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2PlayerData;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class EnterArenaComposer  extends MessageComposer{

	private final SnowWarRoom arena;

	public EnterArenaComposer(final SnowWarRoom room) {
		this.arena = room;
	}

	@Override
	public void compose(IComposer msg) {
		msg.writeInt(0);
		msg.writeInt(arena.ArenaType.ArenaType);
		msg.writeInt(SnowWar.TEAMS.length);
		msg.writeInt(arena.players.size());
		for (final HumanGameObject Player : arena.players.values()) {
			SerializeGame2PlayerData.parse(msg, Player);
		}
		SerializeArena.parse(msg, arena);
	}

	@Override
	public short getId() {
		return 1017;
	}
}
