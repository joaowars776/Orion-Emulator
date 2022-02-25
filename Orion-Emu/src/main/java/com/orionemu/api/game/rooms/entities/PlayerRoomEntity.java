package com.orionemu.api.game.rooms.entities;

import com.orionemu.api.game.players.BasePlayer;

public interface PlayerRoomEntity extends RoomEntity {
    BasePlayer getPlayer();
}
