package com.orionemu.games.snowwar.gameevents;

import com.orionemu.games.snowwar.gameobjects.HumanGameObject;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class MakeSnowBall extends Event {
	public HumanGameObject player;

	public MakeSnowBall(final HumanGameObject player) {
		EventType = Event.MAKENOWBALL;
		this.player = player;
	}

	@Override
	public void apply() {
		player.makeSnowBall();
	}
}
