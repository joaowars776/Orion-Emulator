package com.orionemu.server.network.messages.incoming.room.pets;

import com.orionemu.server.game.achievements.types.AchievementType;
import com.orionemu.server.game.rooms.objects.entities.types.PetEntity;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.objects.misc.Position;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.game.rooms.types.mapping.RoomTile;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.pets.ScratchPetNotificationMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class ScratchPetMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int petId = msg.readInt();

        if (client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) return;

        Room room = client.getPlayer().getEntity().getRoom();
        PetEntity petEntity = room.getEntities().getEntityByPetId(petId);
        PlayerEntity playerEntity = client.getPlayer().getEntity();

        if (petEntity == null) return;

        if(!playerEntity.getPosition().touching(petEntity.getPosition())) {
            Position position = petEntity.getPosition().squareInFront(petEntity.getBodyRotation());

            RoomTile tile = room.getMapping().getTile(position.getX(), position.getY());

            if(tile == null) {
                position = petEntity.getPosition().squareBehind(petEntity.getBodyRotation());
                tile = room.getMapping().getTile(position.getX(), position.getY());
            }

            if(tile != null) {
                if(!tile.isReachable(playerEntity)) {
                    petEntity.getPetAI().waitForInteraction();
                    petEntity.cancelWalk();

                    scratch(client, petEntity);
                } else {
                    playerEntity.moveTo(position);
                    tile.scheduleEvent(playerEntity.getId(), (e) -> scratch(((PlayerEntity) e).getPlayer().getSession(), petEntity));
                }
            } else {
                return;
            }

            return;
        }

        this.scratch(client, petEntity);
    }

    private void scratch(Session client, PetEntity petEntity) {
        if(client.getPlayer() == null || client.getPlayer().getEntity() == null) {
            return;
        }

        client.getPlayer().getEntity().lookTo(petEntity.getPosition().getX(), petEntity.getPosition().getY());
        client.getPlayer().getEntity().markNeedsUpdate();

        client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new ScratchPetNotificationMessageComposer(petEntity));

        client.getPlayer().getEntity().carryItem(999999999, 5);
        petEntity.getPetAI().onScratched();

        client.getPlayer().getAchievements().progressAchievement(AchievementType.PET_RESPECT_GIVEN, 1);
    }
}