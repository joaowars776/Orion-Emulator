package com.orionemu.server.network.messages.outgoing.room.settings;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.game.rooms.types.RoomWriter;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class RoomSettingsDataMessageComposer extends MessageComposer {
    private final Room room;
    private final boolean staff;

    public RoomSettingsDataMessageComposer(Room room, boolean staff) {
        this.room = room;
        this.staff = staff;
    }

    @Override
    public short getId() {
        return Composers.RoomSettingsDataMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(room.getId());
        msg.writeString(room.getData().getName());
        msg.writeString(room.getData().getDescription());
        msg.writeInt(RoomWriter.roomAccessToNumber(room.getData().getAccess()));
        msg.writeInt(room.getData().getCategory().getId());
        msg.writeInt(room.getData().getMaxUsers());
        msg.writeInt(staff ? 500 : OrionSettings.roomMaxPlayers);
        msg.writeInt(room.getData().getTags().length);

        for (String tag : room.getData().getTags()) {
            msg.writeString(tag);
        }

        msg.writeInt(0); // TODO: rights
        msg.writeInt(room.getData().isAllowPets() ? 1 : 0); // allow pets
        msg.writeInt(1); // allow pets eat
        msg.writeInt(room.getData().getAllowWalkthrough() ? 1 : 0);
        msg.writeInt(room.getData().getHideWalls() ? 1 : 0);
        msg.writeInt(room.getData().getWallThickness());
        msg.writeInt(room.getData().getFloorThickness());
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(1);
        msg.writeInt(14);
        msg.writeInt(0);
        msg.writeInt(1);
        msg.writeInt(1);
    }
}
