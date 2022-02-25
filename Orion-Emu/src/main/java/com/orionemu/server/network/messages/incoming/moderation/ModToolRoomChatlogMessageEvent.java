package com.orionemu.server.network.messages.incoming.moderation;

import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.types.RoomData;
import com.orionemu.server.logging.LogManager;
import com.orionemu.server.logging.database.queries.LogQueries;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.moderation.ModToolRoomChatlogMessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class ModToolRoomChatlogMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int context = msg.readInt();
        int roomId = msg.readInt();

        if (!client.getPlayer().getPermissions().getRank().modTool()) {
            client.disconnect();
            return;
        }

        if (!LogManager.ENABLED) {
            client.send(new AdvancedAlertMessageComposer("Notice", "Logging is not currently enabled, please contact your system administrator to enable it."));
            return;
        }

        RoomData roomData = RoomManager.getInstance().getRoomData(roomId);

        if (roomData != null) {
            client.send(new ModToolRoomChatlogMessageComposer(roomData.getId(), roomData.getName(), LogQueries.getChatlogsForRoom(roomData.getId())));
        } else {
            client.send(new AdvancedAlertMessageComposer("Notice", "There seems to be an issue with fetching the logs for this room (ID: " + roomId + ", Context: " + context + ")"));
        }
    }
}
