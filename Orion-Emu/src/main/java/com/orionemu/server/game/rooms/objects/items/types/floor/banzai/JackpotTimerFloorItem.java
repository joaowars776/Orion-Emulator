package com.orionemu.server.game.rooms.objects.items.types.floor.banzai;

import com.orionemu.server.game.rooms.objects.entities.RoomEntity;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.types.Room;


public class JackpotTimerFloorItem extends RoomItemFloor {
    private String lastTime;

    public JackpotTimerFloorItem(long id, int itemId, Room room, int owner, String ownerName, int x, int y, double z, int rotation, String data) {
        super(id, itemId, room, owner, ownerName, x, y, z, rotation, data);
    }

    @Override
    public boolean onInteract(RoomEntity entity, int requestData, boolean isWiredTriggered) {

        return true;
    }

    @Override
    public void onPickup() {
        if (this.getRoom().getGame().getInstance() != null) {
            this.getRoom().getGame().getInstance().onGameEnds();
            this.getRoom().getGame().stop();
        }
    }

    @Override
    public String getDataObject() {
        return this.lastTime != null && !this.lastTime.isEmpty() ? this.lastTime : this.getExtraData();
    }
}
