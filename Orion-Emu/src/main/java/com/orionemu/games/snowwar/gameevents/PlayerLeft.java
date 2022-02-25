package com.orionemu.games.snowwar.gameevents;

import com.orionemu.games.snowwar.gameobjects.HumanGameObject;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class PlayerLeft extends Event {
	public HumanGameObject player;

	public PlayerLeft(final HumanGameObject player) {
		EventType = Event.PLAYERLEFT;
		this.player = player;
	}

	@Override
	public void apply() {
		player.currentSnowWar.queueDeleteObject(player);
		player.cleanTiles();
	}
}
