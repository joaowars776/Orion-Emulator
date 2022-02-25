package com.orionemu.games.snowwar.gameevents;

import com.orionemu.games.snowwar.gameobjects.HumanGameObject;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class UserMove extends Event {
	public HumanGameObject player;
	public int x;
	public int y;

	public UserMove(final HumanGameObject player, final int x, final int y) {
		EventType = Event.MOVE;
		this.player = player;
		this.x = x;
		this.y = y;
	}

	@Override
	public void apply() {
		player.setMove(x, y);
	}
}
