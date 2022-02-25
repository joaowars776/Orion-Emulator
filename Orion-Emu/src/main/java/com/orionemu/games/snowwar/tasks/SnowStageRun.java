package com.orionemu.games.snowwar.tasks;

import com.orionemu.games.snowwar.SnowWar;
import com.orionemu.games.snowwar.SnowWarRoom;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.StageRunningComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class SnowStageRun {
	public static void exec(SnowWarRoom room) {
		room.broadcast(new StageRunningComposer(SnowWar.GAMESECONDS));
	}
}
