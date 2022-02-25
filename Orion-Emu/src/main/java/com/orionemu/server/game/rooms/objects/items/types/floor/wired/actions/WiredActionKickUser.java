package com.orionemu.server.game.rooms.objects.items.types.floor.wired.actions;

import com.orionemu.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.objects.items.RoomItemFactory;
import com.orionemu.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;


public class WiredActionKickUser extends WiredActionShowMessage {

    /**
     * The default constructor
     *
     * @param id       The ID of the item
     * @param itemId   The ID of the item definition
     * @param room     The instance of the room
     * @param owner    The ID of the owner
     * @param x        The position of the item on the X axis
     * @param y        The position of the item on the Y axis
     * @param z        The position of the item on the z axis
     * @param rotation The orientation of the item
     * @param data     The JSON object associated with this item
     */
    public WiredActionKickUser(long id, int itemId, Room room, int owner, String ownerName, int x, int y, double z, int rotation, String data) {
        super(id, itemId, room, owner, ownerName, x, y, z, rotation, data);
        this.isWhisperBubble = true;
    }

    @Override
    public void onEventComplete(WiredItemEvent event) {
        if(event.entity != null && event.type == 1) {
            event.entity.leaveRoom(false, true, true);
            return;
        }

        if (!(event.entity instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity playerEntity = (PlayerEntity) event.entity;

        String kickException = "";

        if (this.getRoom().getData().getOwnerId() == playerEntity.getPlayerId()) {
            kickException = "Room owner";
        }

        if (kickException.isEmpty()) {
            super.onEventComplete(event);

            event.entity.applyEffect(new PlayerEffect(4, 5));
            event.type = 1;

            event.setTotalTicks(RoomItemFactory.getProcessTime(0.9));
            this.queueEvent(event);
        } else {
            playerEntity.getPlayer().getSession().send(new WhisperMessageComposer(playerEntity.getId(), "Wired kick exception: " + kickException));
        }
    }
}
