package com.orionemu.server.network.messages.incoming.room.pets;

import com.orionemu.server.game.pets.data.PetData;
import com.orionemu.server.game.rooms.objects.entities.types.PetEntity;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.user.inventory.PetInventoryMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.pets.RoomPetDao;


public class RemovePetMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        PetEntity entity = client.getPlayer().getEntity().getRoom().getEntities().getEntityByPetId(msg.readInt());

        if (entity == null) {
            return;
        }

        Room room = entity.getRoom();

        boolean isOwner = client.getPlayer().getId() == room.getData().getOwnerId();

        if ((isOwner) || client.getPlayer().getPermissions().hasCommand("room_full_control") || (room.getData().isAllowPets() && entity.getData().getOwnerId() == client.getPlayer().getId())) {
            int ownerId = entity.getData().getOwnerId();

            if (room.getData().isAllowPets() || client.getPlayer().getId() != ownerId) {
                if (NetworkManager.getInstance().getSessions().getByPlayerId(ownerId) != null) {
                    Session petOwner = NetworkManager.getInstance().getSessions().getByPlayerId(ownerId);

                    givePetToPlayer(petOwner, entity.getData());
                }
            } else {
                givePetToPlayer(client, entity.getData());
            }

            RoomPetDao.updatePet(0, 0, 0, entity.getData().getId());
            entity.leaveRoom(false);
        }
    }

    private void givePetToPlayer(Session client, PetData petData) {
        client.getPlayer().getPets().addPet(petData);
        client.send(new PetInventoryMessageComposer(client.getPlayer().getPets().getPets()));
    }
}
