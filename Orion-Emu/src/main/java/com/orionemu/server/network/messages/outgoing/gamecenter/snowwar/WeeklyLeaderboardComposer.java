package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.GamesLeaderboard;
import com.orionemu.games.snowwar.data.SnowWarPlayerData;
import com.orionemu.server.network.messages.composers.MessageComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class WeeklyLeaderboardComposer extends MessageComposer {
	
	private final GamesLeaderboard leaderboard;

	public WeeklyLeaderboardComposer(GamesLeaderboard leaderboard) {
		this.leaderboard = leaderboard;
	}

	@Override
	public void compose(IComposer msg) {
		msg.writeInt(2012);
		msg.writeInt(0); // week offset?
		msg.writeInt(0); // week offset limit?
		msg.writeInt(0); // 0 = this week, other = prev week
		msg.writeInt(23); // day
		msg.writeInt(leaderboard.rankedList.size());
		for(final SnowWarPlayerData player : leaderboard.rankedList) {
			msg.writeInt(player.player.getId());
			msg.writeInt(player.score);
			msg.writeInt(player.rank);
			msg.writeString(player.player.getData().getUsername());
			msg.writeString(player.player.getData().getFigure());
			msg.writeString(player.player.getData().getGender().toUpperCase());
		}
		msg.writeInt(0); // position start or end....
		msg.writeInt(leaderboard.gameId);
	}

	@Override
	public short getId() {
		return 0;
	}
}
