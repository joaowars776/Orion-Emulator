package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.gameobjects.HumanGameObject;
import com.orionemu.server.network.messages.composers.MessageComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class PlayerExitedGameArenaComposer extends MessageComposer {

	private final HumanGameObject player;

	public PlayerExitedGameArenaComposer(HumanGameObject player) {
		this.player = player;
	}

	@Override
	public void compose(IComposer msg) {
		msg.writeInt(player.userId);
		msg.writeInt(player.objectId);
	}

	@Override
	public short getId() {
		return 0;
	}
}
