package com.orionemu.server.network.messages.incoming.user.wardrobe;

import com.orionemu.api.game.players.data.types.IWardrobeItem;
import com.orionemu.server.game.players.components.types.settings.WardrobeItem;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.PlayerDao;
import com.orionemu.server.utilities.JsonUtil;

import java.util.List;


public class SaveWardrobeMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int slot = msg.readInt();
        String figure = msg.readString();
        String gender = msg.readString();

        List<IWardrobeItem> wardrobe = client.getPlayer().getSettings().getWardrobe();

        boolean wardrobeUpdated = false;

        for (IWardrobeItem item : wardrobe) {
            if (item.getSlot() == slot) {
                item.setFigure(figure);
                item.setGender(gender);

                wardrobeUpdated = true;
            }
        }

        if (!wardrobeUpdated) {
            wardrobe.add(new WardrobeItem(slot, gender, figure));
        }

        client.getPlayer().getSettings().setWardrobe(wardrobe);
        PlayerDao.saveWardrobe(JsonUtil.getInstance().toJson(wardrobe), client.getPlayer().getId());
    }
}
