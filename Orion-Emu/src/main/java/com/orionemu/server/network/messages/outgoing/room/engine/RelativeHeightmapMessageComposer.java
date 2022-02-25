package com.orionemu.server.network.messages.outgoing.room.engine;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.rooms.models.RoomModel;
import com.orionemu.server.game.rooms.types.tiles.RoomTileState;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class RelativeHeightmapMessageComposer extends MessageComposer {
    private static char[] characters;

    static {
        characters = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
    }

    private final RoomModel model;

    public RelativeHeightmapMessageComposer(final RoomModel model) {
        this.model = model;
    }

    @Override
    public short getId() {
        return Composers.FloorHeightMapMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(true); // ??

        StringBuilder builder = new StringBuilder();

        for (int y = 0; y < model.getSizeY(); y++) {
            for (int x = 0; x < model.getSizeX(); x++) {
                if (x == model.getDoorX() && y == model.getDoorY()) {
                    builder.append(model.getDoorZ());
                } else if (model.getSquareState()[x][y] == RoomTileState.INVALID) {
                    builder.append("x");
                } else {
                    builder.append(characters[(int) Math.floor(model.getSquareHeight()[x][y] + 0.5d)]);
                }
            }

            builder.append((char) 13);
        }

        msg.writeString(builder.toString());
    }
}
