package com.orionemu.server.network.messages.incoming.room.engine;

import com.orionemu.api.game.rooms.settings.RoomAccessType;
import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.game.rooms.types.RoomData;
import com.orionemu.server.game.rooms.types.RoomWriter;
import com.orionemu.server.network.messages.MessageHandler;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.group.GroupBadgesMessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.avatar.AvatarsMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.engine.FollowRoomDataMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.engine.RoomDataMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.engine.RoomEntryInfoMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.items.FloorItemsMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.items.WallItemsMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.headers.Composers;
import com.orionemu.server.network.messages.protocol.messages.Composer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;


public class FollowRoomInfoMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int roomId = msg.readInt();

        Room room = RoomManager.getInstance().get(roomId);

        if (room == null || room.getData() == null) {
            return;
        }

        boolean skipAuth = false;

        if (room.getData().getAccess() != RoomAccessType.OPEN) { //Sidev det är riktiga spreed som är i chatten och det är spreed nu ;)
            if (room.getRights().hasRights(client.getPlayer().getId())) {
                skipAuth = true;
            } else if (client.getPlayer().isTeleporting() || client.getPlayer().isBypassingRoomAuth()) {
                skipAuth = true;
            }
        }

        if (client.getPlayer().getData().isInHotelView()) {
            client.getPlayer().loadRoom(roomId, room.getData().getPassword());
            client.send(new FollowRoomDataMessageComposer(room.getData()));
            client.send(new RoomEntryInfoMessageComposer(room.getId(), room.getData().getOwnerId() == client.getPlayer().getId() || client.getPlayer().getPermissions().getRank().roomFullControl()));
            client.send(new RoomDataMessageComposer(room));
            client.send(new AvatarsMessageComposer(room));
            return;
        }

        if (client.getPlayer().getEntity() != null && client.getPlayer().getEntity().getRoom() != null) {
            if (roomId != client.getPlayer().getEntity().getRoom().getId()) {
                client.send(new FollowRoomDataMessageComposer(room.getData()));
                client.getPlayer().loadRoom(roomId, room.getData().getPassword());
            }
        }
    }
}
