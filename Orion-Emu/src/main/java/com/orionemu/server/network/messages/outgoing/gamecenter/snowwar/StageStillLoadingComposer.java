package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.gameobjects.HumanGameObject;
import com.orionemu.server.network.messages.composers.MessageComposer;

import java.util.Collection;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class StageStillLoadingComposer extends MessageComposer {

	private final Collection<HumanGameObject> playersLoaded;

	public StageStillLoadingComposer(Collection<HumanGameObject> playersLoaded) {
		this.playersLoaded = playersLoaded;
	}

	@Override
	public void compose(IComposer msg) {
		msg.writeInt(0); // notused
		msg.writeInt(playersLoaded.size());
		for (final HumanGameObject player : playersLoaded) {
			msg.writeInt(player.userId);
		}
	}

	@Override
	public short getId() {
		return 2363;
	}
}
