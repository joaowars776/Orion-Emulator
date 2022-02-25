package com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.games.snowwar.items.BaseItem;
import com.orionemu.games.snowwar.items.Item;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class SerializeItemData {
    public static void parse(final IComposer msg, final BaseItem baseItem, final Item item) {
        msg.writeInt(item.extraData.getType());
        item.extraData.serializeComposer(msg);

		/*if (flags & 0x0100 > 0) {
			Composer.add(10, writer); // unique serial id
			Composer.add(10, writer); // unique size
		}*/
    }
}
