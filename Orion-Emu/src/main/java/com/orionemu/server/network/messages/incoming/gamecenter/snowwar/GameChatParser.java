package com.orionemu.server.network.messages.incoming.gamecenter.snowwar;

import com.orionemu.games.snowwar.SnowWarRoom;
import com.orionemu.games.snowwar.data.SnowWarPlayerData;
import com.orionemu.games.snowwar.gameobjects.HumanGameObject;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.GameChatFromPlayerComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class GameChatParser implements Event {
	@Override
	public void handle(Session client, MessageEvent msg) throws Exception {
		final SnowWarPlayerData snowPlayer = client.snowWarPlayerData;
		if (snowPlayer == null) {
			return;
		}

		final SnowWarRoom room = snowPlayer.currentSnowWar;
		if (room == null) {
			return;
		}

		final String say = msg.readString();

		room.broadcast(new GameChatFromPlayerComposer(snowPlayer.player.getId(), say));
	}
}