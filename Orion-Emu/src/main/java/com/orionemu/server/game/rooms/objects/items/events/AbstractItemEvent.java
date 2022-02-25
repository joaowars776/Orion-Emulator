package com.orionemu.server.game.rooms.objects.items.events;

import com.orionemu.server.game.rooms.objects.entities.RoomEntity;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.tasks.OrionTask;
import com.orionemu.server.tasks.OrionThreadManager;

import java.util.concurrent.TimeUnit;

public abstract class AbstractItemEvent implements OrionTask {
    private final RoomItemFloor floorItem;
    private final RoomEntity entity;

    public AbstractItemEvent(RoomItemFloor floorItem, RoomEntity entity) {
        this.floorItem = floorItem;
        this.entity = entity;
    }

    protected void runIn(double seconds) {
        OrionThreadManager.getInstance().executeSchedule(this, (long) (seconds * 1000), TimeUnit.MILLISECONDS);
    }

    protected RoomItemFloor getFloorItem() {
        return floorItem;
    }

    protected RoomEntity getEntity() {
        return entity;
    }
}
