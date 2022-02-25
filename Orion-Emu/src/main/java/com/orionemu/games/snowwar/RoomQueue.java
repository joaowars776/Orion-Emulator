package com.orionemu.games.snowwar;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.orionemu.api.networking.messages.IMessageComposer;
import com.orionemu.server.network.sessions.Session;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class RoomQueue {
	public SnowWarRoom room;
	public final Map<Integer, Session> players = new ConcurrentHashMap<Integer, Session>(SnowWar.MAXPLAYERS);

	public RoomQueue(SnowWarRoom snowRoom) {
		room = snowRoom;
	}

	public void broadcast(final IMessageComposer Message) {
		for (final Session cn : players.values()) {
			cn.getChannel().writeAndFlush(Message);
		}
	}
}
