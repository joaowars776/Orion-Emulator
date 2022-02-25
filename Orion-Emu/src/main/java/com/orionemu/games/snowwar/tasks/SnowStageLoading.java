package com.orionemu.games.snowwar.tasks;

import java.util.Collection;

import com.orionemu.games.snowwar.SnowWar;
import com.orionemu.games.snowwar.SnowWarRoom;
import com.orionemu.games.snowwar.gameobjects.HumanGameObject;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.StageStillLoadingComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class SnowStageLoading {
	public static void exec(SnowWarRoom room) {
		final Collection<HumanGameObject> playersLoaded = room.getStageLoadedPlayers();

		if(playersLoaded != null) {
			room.broadcast(new StageStillLoadingComposer(playersLoaded));

			if (!playersLoaded.isEmpty()) {
				return;
			}
		}

		for (final HumanGameObject player : room.players.values()) {
			if(!player.stageLoaded) {
				return;
			}
		}

		room.STATUS = SnowWar.STAGE_STARTING;
	}
}
