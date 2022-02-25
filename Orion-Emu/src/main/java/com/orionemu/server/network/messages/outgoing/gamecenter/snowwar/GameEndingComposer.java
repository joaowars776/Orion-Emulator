package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.SnowWar;
import com.orionemu.games.snowwar.SnowWarRoom;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2GameResult;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2SnowWarGameStats;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2TeamScoreData;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class GameEndingComposer extends MessageComposer {

	private final SnowWarRoom arena;

	public GameEndingComposer(SnowWarRoom arena) {
		this.arena = arena;
	}

	@Override
	public void compose(IComposer msg) {
		msg.writeInt(0);
		SerializeGame2GameResult.parse(msg, arena);
		msg.writeInt(SnowWar.TEAMS.length);
		for (final int teamId : SnowWar.TEAMS) {
			SerializeGame2TeamScoreData.parse(msg, teamId, arena.TeamScore[teamId-1], arena.TeamPlayers.get(teamId).values());
		}
		SerializeGame2SnowWarGameStats.parse(msg, arena);
	}

	@Override
	public short getId() {
		return 2013;
	}
}
