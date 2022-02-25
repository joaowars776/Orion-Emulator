package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class GameDirectoryStatusComposer extends MessageComposer {
    public static final int ENABLED = 0;
    public static final int UNKNOW1 = 1;
    public static final int UNKNOW2 = 2;
    public static final int UNKNOW3 = 3;
    
    private final int state;

	public GameDirectoryStatusComposer(int state) {
		this.state = state;
	}

	@Override
	public void compose(IComposer msg) {
		msg.writeInt(state);
		msg.writeInt(0); // can't play in x time of seconds..
		msg.writeInt(0); // snowwar.promotion
		msg.writeInt(-1);
	}

	@Override
	public short getId() {
		return 3005;
	}
}
