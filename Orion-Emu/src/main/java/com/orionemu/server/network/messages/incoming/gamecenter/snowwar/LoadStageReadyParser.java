package com.orionemu.server.network.messages.incoming.gamecenter.snowwar;

import com.orionemu.games.snowwar.data.SnowWarPlayerData;
import com.orionemu.games.snowwar.gameobjects.HumanGameObject;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class LoadStageReadyParser implements Event {
	@Override
	public void handle(Session client, MessageEvent msg) throws Exception {
		final SnowWarPlayerData snowPlayer = client.snowWarPlayerData;
		if(snowPlayer.currentSnowWar == null) {
			return;
		}

		final HumanGameObject humanObject = snowPlayer.humanObject;
		if(humanObject == null) {
			return;
		}

		//Main.in.ReadInt(); // always is 100
		snowPlayer.currentSnowWar.stageLoaded(humanObject);
	}
}
