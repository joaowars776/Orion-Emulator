package com.orionemu.games.snowwar.tasks;

import java.util.ArrayList;

import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.StageStartingComposer;
import com.orionemu.server.network.sessions.Session;
import io.netty.channel.Channel;

import com.orionemu.games.snowwar.SnowWarRoom;
import com.orionemu.games.snowwar.gameobjects.GameItemObject;
import com.orionemu.games.snowwar.gameobjects.HumanGameObject;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class SnowStageStarting {
	public static void exec(SnowWarRoom room) {
		room.gameObjects.clear();

		room.ArenaType.gameObjects(room.gameObjects, room);

		for (final GameItemObject obj : room.gameObjects.values()) {
			// TODO: use "addGameObject" in ArenaType.gameObjects and set objectId
			obj._active = true;
			obj.objectId = room.objectIdCounter++;
		}

		for (final HumanGameObject player : room.players.values()) {
			room.addGameObject(player);
		}

		room.checksum = 0;
		for (final GameItemObject Object : room.gameObjects.values()) {
			Object.GenerateCHECKSUM(room, 1);
		}

		room.broadcast(new StageStartingComposer(room));

		room.fullGameStatusQueue = new ArrayList<>();
	}
}
