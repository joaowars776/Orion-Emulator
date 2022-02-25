package com.orionemu.server.network.messages.incoming.moderation;

import com.orionemu.server.logging.LogManager;
import com.orionemu.server.logging.database.queries.LogQueries;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.moderation.ModToolRoomVisitsMessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.PlayerDao;
import com.google.common.collect.Lists;


public class ModToolRoomVisitsMessageEvent implements Event {

    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int playerId = msg.readInt();

        if (!LogManager.ENABLED) {
            client.send(new AdvancedAlertMessageComposer("Notice", "Logging is not currently enabled, please contact your system administrator to enable it."));
            client.send(new ModToolRoomVisitsMessageComposer(playerId, PlayerDao.getUsernameByPlayerId(playerId), Lists.newArrayList()));
        }

        if (LogManager.ENABLED)
            client.send(new ModToolRoomVisitsMessageComposer(playerId, PlayerDao.getUsernameByPlayerId(playerId), LogQueries.getLastRoomVisits(playerId, 100)));
    }
}
