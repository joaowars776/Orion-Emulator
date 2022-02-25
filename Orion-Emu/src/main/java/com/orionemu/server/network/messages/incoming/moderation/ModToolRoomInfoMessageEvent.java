package com.orionemu.server.network.messages.incoming.moderation;

import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.moderation.ModToolRoomInfoMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class ModToolRoomInfoMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int roomId = msg.readInt();

        if (!client.getPlayer().getPermissions().getRank().modTool()) {
            // fuck off
            client.getLogger().error(
                    ModToolUserInfoMessageEvent.class.getName() + " - tried to view room info for room: " + roomId);
            client.disconnect();
            return;
        }

        Room room = RoomManager.getInstance().get(roomId);

        if (room == null)
            return;

        client.send(new ModToolRoomInfoMessageComposer(room));
    }
}
