package com.orionemu.server.network.messages.incoming.user.details;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.PlayerDao;


public class UpdateChatStyleMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        boolean useOldChat = msg.readBoolean();

        if (client.getPlayer() == null) {
            return;
        }

        client.getPlayer().getSettings().setUseOldChat(useOldChat);
        PlayerDao.saveChatStyle(useOldChat, client.getPlayer().getId());
    }
}
