package com.orionemu.server.network.messages.incoming.room.settings;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.rooms.objects.entities.RoomEntity;
import com.orionemu.server.game.rooms.objects.entities.RoomEntityType;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.settings.RoomMuteMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class MuteRoomMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if (client.getPlayer().getEntity() == null)
            return;

        Room room = client.getPlayer().getEntity().getRoom();

        if (room.getData().getOwnerId() != client.getPlayer().getId() && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            return;
        }
        
        if (room.hasRoomMute()) {
            for(RoomEntity entity : room.getEntities().getAllEntities().values()) {
                entity.setRoomMuted(false);
            }

            for (RoomEntity entity : client.getPlayer().getEntity().getRoom().getEntities().getPlayerEntities()) {
                if (entity.getEntityType() == RoomEntityType.PLAYER) {
                    PlayerEntity playerEntity = (PlayerEntity) entity;
                    playerEntity.getPlayer().getSession().send(new WhisperMessageComposer(playerEntity.getId(), Locale.getOrDefault("event.room.unmute", "You are now able to chat again :-)")));
                }
            }

            room.setRoomMute(false);
        } else {
            for(RoomEntity entity : room.getEntities().getAllEntities().values()) {
                entity.setRoomMuted(true);
            }

            room.setRoomMute(true);
            
            for (RoomEntity entity : client.getPlayer().getEntity().getRoom().getEntities().getPlayerEntities()) {
                if (entity.getEntityType() == RoomEntityType.PLAYER) {
                    PlayerEntity playerEntity = (PlayerEntity) entity;
                    playerEntity.getPlayer().getSession().send(new WhisperMessageComposer(playerEntity.getId(), Locale.getOrDefault("event.room.muted", "The room owner has muted the room.")));
                }
            }
        }

        room.getEntities().broadcastMessage(new RoomMuteMessageComposer(room.hasRoomMute()));
    }
}
