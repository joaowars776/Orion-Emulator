package com.orionemu.server.network.messages.incoming.user.details;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.PlayerDao;


public class IgnoreInvitationsMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        boolean ignoreRoomInvitations = msg.readBoolean();

        client.getPlayer().getSettings().setIgnoreInvites(ignoreRoomInvitations);
        PlayerDao.saveIgnoreInvitations(ignoreRoomInvitations, client.getPlayer().getId());
    }
}
