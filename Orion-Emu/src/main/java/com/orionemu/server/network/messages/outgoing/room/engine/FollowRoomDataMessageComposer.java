package com.orionemu.server.network.messages.outgoing.room.engine;


import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.rooms.types.RoomData;
import com.orionemu.server.game.rooms.types.RoomWriter;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class FollowRoomDataMessageComposer extends MessageComposer {
    private final RoomData roomData;

    public FollowRoomDataMessageComposer(final RoomData room) {
        this.roomData = room;
    }

    @Override
    public short getId() {
        return Composers.GetGuestRoomResultMessageComposer;
    }

    @Override
    public void compose(IComposer composer) {
        RoomWriter.entryData(this.roomData, composer);
    }
}
