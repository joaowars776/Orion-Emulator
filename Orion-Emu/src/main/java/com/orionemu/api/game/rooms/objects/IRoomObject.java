package com.orionemu.api.game.rooms.objects;

import com.orionemu.api.game.rooms.IRoom;
import com.orionemu.api.game.rooms.util.IPosition;

public interface IRoomObject {
    IPosition getPosition();

    boolean isAtDoor();

    IRoom getRoom();
}
