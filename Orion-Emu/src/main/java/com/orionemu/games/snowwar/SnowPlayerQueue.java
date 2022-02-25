package com.orionemu.games.snowwar;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.orionemu.games.snowwar.gameevents.PlayerLeft;
import com.orionemu.games.snowwar.gameobjects.HumanGameObject;
import com.orionemu.games.snowwar.thread.SnowWarTask;
import com.orionemu.server.game.players.data.PlayerData;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.gamecenter.YouArePlayingGameComposer;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.*;
import com.orionemu.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.orionemu.server.network.sessions.Session;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class SnowPlayerQueue {
	private static int roomCounter;
	public static final Map<Integer, RoomQueue> roomQueue = new ConcurrentHashMap<Integer, RoomQueue>(100);

	public static void addPlayerInQueue(final Session cn) {
		final PlayerData playerData = cn.getPlayer().getData();
		
		RoomQueue pickRoom = null;
		if(roomQueue.isEmpty()) {
			pickRoom = new RoomQueue(new SnowWarRoom(++roomCounter));
			roomQueue.put(pickRoom.room.roomId, pickRoom);
			String username = cn.getPlayer().getData().getUsername();
			NetworkManager.getInstance().getSessions().broadcast(
					new NotificationMessageComposer("generic", username + " söker en motståndare till SnowStorm, törs du?")
			);
		} else {
			for(final RoomQueue room : roomQueue.values()) {
				if(room.players.size() < SnowWar.MAXPLAYERS) {
					pickRoom = room;
					break;
				}
			}

			// all rooms full
			if(pickRoom == null) {
				pickRoom = new RoomQueue(new SnowWarRoom(++roomCounter));
				roomQueue.put(pickRoom.room.roomId, pickRoom);
			}
		}

		if (pickRoom.players.isEmpty()) {
			pickRoom.room.Owner = playerData.getUsername();
		}

		cn.snowWarPlayerData.setHumanObject(new HumanGameObject(pickRoom.room, 0));
		cn.snowWarPlayerData.humanObject.status = SnowWar.INLOBBY;
		cn.snowWarPlayerData.setRoom(pickRoom.room);

		pickRoom.broadcast(new UserJoinedGameComposer(cn));

		pickRoom.players.put(playerData.getId(), cn);

		if (pickRoom.room.Owner.equals(playerData.getUsername())) {
			cn.getChannel().writeAndFlush(new GameCreatedComposer(pickRoom));
		} else {
			cn.getChannel().writeAndFlush(new GameLongDataComposer(pickRoom));
		}

		if (pickRoom.room.TimeToStart < 20 && pickRoom.room.STATUS == SnowWar.TIMER_TOLOBBY) {
			cn.send(new StartCounterComposer(pickRoom.room.TimeToStart));
		}

		if(pickRoom.players.size() >= SnowWar.MINPLAYERS) {
			startLoading(pickRoom);
		}
	}

	public static void playerExit(SnowWarRoom room, HumanGameObject playerObject) {
		final RoomQueue queue = roomQueue.get(room.roomId);
		if(queue == null) {
			room.players.remove(playerObject.userId);
			room.TeamPlayers.get(playerObject.team).remove(playerObject.userId);

			if (room.STATUS == SnowWar.ARENA) {
				synchronized(room.gameEvents) {
					room.gameEvents.add(new PlayerLeft(playerObject));
				}
				return;
			}

			room.broadcast(new UserLeftGameComposer(playerObject.userId));
		} else {
			queue.broadcast(new UserLeftGameComposer(playerObject.userId));
			queue.players.remove(playerObject.userId);
		}

		playerObject.cleanData();
	}

	public static void roomLoaded(SnowWarRoom room) {
		final RoomQueue queue = roomQueue.remove(room.roomId);
		if(queue == null) {
			return;
		}

		int pickTeam = 0;
		for(final Session cn : queue.players.values()) {
			room.players.put(cn.getPlayer().getId(), cn.snowWarPlayerData.humanObject);
			int team = 1 + (++pickTeam % SnowWar.TEAMS.length);
			cn.snowWarPlayerData.humanObject.team = team;
			room.TeamPlayers.get(team).put(cn.getPlayer().getData().getId(), cn.snowWarPlayerData.humanObject);
		}
				
		queue.broadcast(new GameStartedComposer(queue));
		queue.broadcast(new InArenaQueueComposer(1));
		queue.broadcast(new YouArePlayingGameComposer(true));
		room.broadcast(new EnterArenaComposer(room));

		for (final HumanGameObject player : room.players.values()) {
			player.status = SnowWar.INARENA;
			if (player.cn.getPlayer().getEntity() != null) {
				player.cn.getPlayer().getEntity().leaveRoom(false, false, false);
			}
			room.broadcast(new ArenaEnteredComposer(player));
		}

		room.broadcast(new StageLoadComposer());
	}

	private static void startLoading(RoomQueue queue) {
		final SnowWarRoom room = queue.room;
		if(room.STATUS == SnowWar.TIMER_TOLOBBY) {
			return;
		}

		room.TimeToStart = 20;
		room.STATUS = SnowWar.TIMER_TOLOBBY;

		queue.broadcast(new StartCounterComposer(room.TimeToStart));

		SnowWarTask.addTask(new SnowWarTask(room), 0, 1000);
	}
}