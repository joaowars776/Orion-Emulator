package com.orionemu.server.network.messages.incoming.room.moderation;

import com.orionemu.server.game.rooms.objects.entities.RoomEntityStatus;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.permissions.YouAreControllerMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.permissions.GiveRoomRightsMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.PlayerDao;


public class GiveRightsMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int playerId = msg.readInt();

        if (playerId == -1) return;

        Room room = client.getPlayer().getEntity().getRoom();

        if (room == null || (room.getData().getOwnerId() != client.getPlayer().getId() && !client.getPlayer().getPermissions().getRank().roomFullControl())) {
            return;
        }

        PlayerEntity playerEntity = room.getEntities().getEntityByPlayerId(playerId);

        if (room.getRights().hasRights(playerId)) {
            return;
        }

        room.getRights().addRights(playerId);
        client.send(new GiveRoomRightsMessageComposer(room.getId(), playerId, playerEntity != null ? playerEntity.getUsername() : PlayerDao.getUsernameByPlayerId(playerId)));

        if (playerEntity != null) {
            playerEntity.removeStatus(RoomEntityStatus.CONTROLLER);
            playerEntity.addStatus(RoomEntityStatus.CONTROLLER, "1");

            playerEntity.markNeedsUpdate();
            playerEntity.getPlayer().getSession().send(new YouAreControllerMessageComposer(1));
        }
    }
}
