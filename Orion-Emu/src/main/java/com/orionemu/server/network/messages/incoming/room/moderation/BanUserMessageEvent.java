package com.orionemu.server.network.messages.incoming.room.moderation;

import com.orionemu.api.game.rooms.settings.RoomBanState;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class BanUserMessageEvent implements Event {

    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if (client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) {
            return;
        }

        Room room = client.getPlayer().getEntity().getRoom();

        if (!room.getRights().hasRights(client.getPlayer().getId()) && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            return;
        }

        if (client.getPlayer().getId() != room.getData().getOwnerId() && room.getData().getBanState() != RoomBanState.RIGHTS && !client.getPlayer().getPermissions().getRank().roomFullControl())
            return;

        int userId = msg.readInt();
        int junk = msg.readInt();
        String time = msg.readString();

        int banLength;

        switch (time) {
            case "RWUAM_BAN_USER_HOUR":
                banLength = 3600;
                break;

            case "RWUAM_BAN_USER_DAY":
                banLength = (3600) * 24;
                break;

            default:
                banLength = -1;
                break;
        }

        int expireTimestamp = banLength == -1 ? banLength : (int) Orion.getTime() + banLength;

        PlayerEntity playerEntity = room.getEntities().getEntityByPlayerId(userId);

        if (playerEntity != null) {
            if (room.getData().getOwnerId() == playerEntity.getPlayerId() || !playerEntity.getPlayer().getPermissions().getRank().roomKickable()) {
                return;
            }

            room.getRights().addBan(userId, playerEntity.getUsername(), expireTimestamp);
            playerEntity.leaveRoom(false, true, true);
        }
    }
}
