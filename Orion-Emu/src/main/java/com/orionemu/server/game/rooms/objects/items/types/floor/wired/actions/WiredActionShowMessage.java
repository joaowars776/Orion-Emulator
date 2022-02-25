package com.orionemu.server.game.rooms.objects.items.types.floor.wired.actions;

import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.orionemu.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;


public class WiredActionShowMessage extends WiredActionItem {

    protected boolean isWhisperBubble = false;

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
    public WiredActionShowMessage(long id, int itemId, Room room, int owner, String ownerName, int x, int y, double z, int rotation, String data) {
        super(id, itemId, room, owner, ownerName, x, y, z, rotation, data);
    }

    @Override
    public boolean requiresPlayer() {
        return true;
    }

    @Override
    public int getInterface() {
        return 7;
    }

    @Override
    public void onEventComplete(WiredItemEvent event) {
        if (event.entity == null || !(event.entity instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity playerEntity = ((PlayerEntity) event.entity);

        if(playerEntity.getPlayer() == null || playerEntity.getPlayer().getSession() == null) {
            return;
        }

        if(this.getWiredData() == null || this.getWiredData().getText() == null || this.getWiredData().getText().isEmpty()) {
            return;
        }

        playerEntity.getPlayer().getSession().send(new WhisperMessageComposer(playerEntity.getId(), this.getWiredData().getText(), isWhisperBubble ? 0 : 34));
    }
}