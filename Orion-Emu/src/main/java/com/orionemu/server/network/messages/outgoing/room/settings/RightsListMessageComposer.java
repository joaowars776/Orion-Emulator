package com.orionemu.server.network.messages.outgoing.room.settings;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;
import com.orionemu.server.storage.queries.player.PlayerDao;

import java.util.List;


public class RightsListMessageComposer extends MessageComposer {
    private final int roomId;
    private final List<Integer> playersWithRights;

    public RightsListMessageComposer(int roomId, List<Integer> playersWithRights) {
        this.roomId = roomId;
        this.playersWithRights = playersWithRights;
    }

    @Override
    public short getId() {
        return Composers.RoomRightsListMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(roomId);
        msg.writeInt(playersWithRights.size());

        for (Integer id : playersWithRights) {
            msg.writeInt(id);

            String username = PlayerDao.getUsernameByPlayerId(id);
            msg.writeString(username != null ? username : "Placeholder");
        }
    }
}
