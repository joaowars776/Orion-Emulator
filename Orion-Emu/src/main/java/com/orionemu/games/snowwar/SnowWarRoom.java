package com.orionemu.games.snowwar;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.orionemu.api.networking.messages.IMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.utilities.RandomInteger;
import io.netty.channel.Channel;

import com.orionemu.games.snowwar.gameevents.Event;
import com.orionemu.games.snowwar.gameobjects.HumanGameObject;
import io.netty.channel.ChannelHandlerContext;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class SnowWarRoom extends SynchronizedGameStage {
	public int[] TeamScore = new int[SnowWar.TEAMS.length];
	public int checksum;
	public int roomId;
	public int Result; // Result. 2 = match scores, 0,1 or other = do the same
	public int STATUS; // 0 = SnowWar.FINDPLAYERS
	public int TimeToStart;
	public int Turn;
	public int Winner;
	public String Name;
	public String Owner;
	public boolean LobbyFull;
	public SnowWarArenaBase ArenaType;
	public HumanGameObject MostHits;
	public HumanGameObject MostKills;
	public SnowWarGameStage map;

	public final Map<Integer, Map<Integer, HumanGameObject>> TeamPlayers = new ConcurrentHashMap<Integer, Map<Integer, HumanGameObject>>(SnowWar.TEAMS.length);
	public final Map<Integer, HumanGameObject> players = new ConcurrentHashMap<Integer, HumanGameObject>(SnowWar.MAXPLAYERS);

	public final List<Event> gameEvents = Collections.synchronizedList(new ArrayList<>());

	public List<ChannelHandlerContext> fullGameStatusQueue;

	public SnowWarRoom(int id) {
		super();

		ArenaType = SnowWar.ArenaTypes[RandomInteger.getRandomNumber(0, SnowWar.ArenaTypes.length - 1)];
		roomId = id;
		Name = "SnowStorm level " + ArenaType.ArenaType;
		map = new SnowWarGameStage();
		map.initialize(ArenaType);
		for (final int TeamId : SnowWar.TEAMS) {
			TeamPlayers.put(TeamId, new ConcurrentHashMap<Integer, HumanGameObject>());
		}
	}

	public void broadcast(final IMessageComposer Message) {
		for (final HumanGameObject player : players.values()) {
			if (player.currentSnowWar != null) {
				player.cn.getChannel().writeAndFlush(Message);
			}
		}
	}

	private Map<Integer, HumanGameObject> stageLoadedPlayers;

	public Collection<HumanGameObject> getStageLoadedPlayers() {
		if(stageLoadedPlayers == null) {
			return null;
		}

		final Collection<HumanGameObject> result = stageLoadedPlayers.values();
		stageLoadedPlayers = null;

		return result;
	}

	public void stageLoaded(HumanGameObject humanObject) {
		if(stageLoadedPlayers == null) {
			stageLoadedPlayers = new ConcurrentHashMap<Integer, HumanGameObject>();
		}

		stageLoadedPlayers.put(humanObject.objectId, humanObject);
		humanObject.stageLoaded = true;
	}
}