package com.orionemu.server.network.messages.incoming.moderation;

import com.orionemu.server.game.moderation.chatlog.UserChatlogContainer;
import com.orionemu.server.logging.LogManager;
import com.orionemu.server.logging.database.queries.LogQueries;
import com.orionemu.server.logging.entries.RoomVisitLogEntry;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.moderation.ModToolUserChatlogMessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class ModToolUserChatlogMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int userId = msg.readInt();

        if (!client.getPlayer().getPermissions().getRank().modTool()) {
            return;
        }

        if (!LogManager.ENABLED)
            client.send(new AdvancedAlertMessageComposer("Notice", "Logging is not currently enabled, please contact your system administrator to enable it."));

        UserChatlogContainer chatlogContainer = new UserChatlogContainer();

        for (RoomVisitLogEntry visit : LogQueries.getLastRoomVisits(userId, 50)) {
            chatlogContainer.addAll(visit.getRoomId(), LogQueries.getChatlogsByCriteria(visit.getPlayerId(), visit.getRoomId(), visit.getEntryTime(), visit.getExitTime()));
        }

        client.send(new ModToolUserChatlogMessageComposer(userId, chatlogContainer));
    }
}
