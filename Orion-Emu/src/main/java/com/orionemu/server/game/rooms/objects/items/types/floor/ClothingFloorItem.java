package com.orionemu.server.game.rooms.objects.items.types.floor;

import com.orionemu.server.game.rooms.objects.items.types.DefaultFloorItem;
import com.orionemu.server.game.rooms.types.Room;

public class ClothingFloorItem extends DefaultFloorItem {
    public ClothingFloorItem(long id, int itemId, Room room, int owner, String ownerName, int x, int y, double z, int rotation, String data) {
        super(id, itemId, room, owner, ownerName, x, y, z, rotation, data);
    }
}
