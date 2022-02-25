package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.gameobjects.HumanGameObject;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2PlayerData;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class ArenaEnteredComposer extends MessageComposer {

	private final HumanGameObject player;

	public ArenaEnteredComposer(final HumanGameObject player) {
		this.player = player;
	}

	@Override
	public void compose(IComposer msg) {
		SerializeGame2PlayerData.parse(msg, this.player);
	}

	@Override
	public short getId() {
		return 806;
	}
}
