package com.orionemu.games.snowwar;

import com.orionemu.games.snowwar.items.Item;
import com.orionemu.games.snowwar.items.StringStuffData;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class GamefuseObject extends Item {
	public int X;
	public int Y;
	public int Rot;
	public int Z;

	public GamefuseObject() {
		extraData = new StringStuffData(null);
	}
}
