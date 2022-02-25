package com.orionemu.games.snowwar.gameevents;

import com.orionemu.games.snowwar.gameobjects.MachineGameObject;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class AddBallToMachine extends Event {
	public MachineGameObject gameItem;

	public AddBallToMachine(final MachineGameObject gameItem) {
		EventType = Event.ADDBALLTOMACHINE;
		this.gameItem = gameItem;
	}

	@Override
	public void apply() {
		gameItem.addSnowBall();
	}
}
