package com.orionemu.server.network.messages.outgoing.room.engine;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.rooms.models.RoomModel;
import com.orionemu.server.game.rooms.types.tiles.RoomTileState;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class HeightmapMessageComposer extends MessageComposer {
    private final RoomModel roomModel;

    public HeightmapMessageComposer(final RoomModel roomModel) {
        this.roomModel = roomModel;
    }

    @Override
    public short getId() {
        return Composers.HeightMapMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(roomModel.getSizeX());
        msg.writeInt(roomModel.getSizeX() * roomModel.getSizeY());

        for (int y = 0; y < roomModel.getSizeY(); y++) {
            for (int x = 0; x < roomModel.getSizeX(); x++) {
                if (roomModel.getSquareState()[x][y] == RoomTileState.INVALID) {
                    msg.writeShort(16191);
                } else if (roomModel.getDoorY() == y && roomModel.getDoorX() == x) {
                    msg.writeShort(0);
                } else {
                    msg.writeShort((short) roomModel.getSquareHeight()[x][y]);
                }
            }
        }
    }
}
