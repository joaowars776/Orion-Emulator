package com.orionemu.server.network.messages.incoming.navigator.updated;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.storage.queries.player.PlayerDao;

public class ResizeNavigatorMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final int x = msg.readInt();
        final int y = msg.readInt();
        final int width = msg.readInt();
        final int height = msg.readInt();
        final boolean savedSearchesVisible = msg.readBoolean();

        client.getPlayer().getSettings().setNavigatorX(x);
        client.getPlayer().getSettings().setNavigatorY(y);
        client.getPlayer().getSettings().setNavigatorHeight(height);
        client.getPlayer().getSettings().setNavigatorWidth(width);
        client.getPlayer().getSettings().setNavigatorShowSearches(savedSearchesVisible);

        PlayerDao.saveNavigatorSettings(x, y, height, width, savedSearchesVisible, client.getPlayer().getId());
    }
}
