package com.orionemu.server.network.messages.outgoing.catalog.groups;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.types.RoomData;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;
import com.google.common.collect.Lists;

import java.util.List;


public class GroupPartsMessageComposer extends MessageComposer {

    private final List<Integer> availableRooms;

    public GroupPartsMessageComposer(final List<Integer> rooms) {
        this.availableRooms = Lists.newArrayList();

        for (Integer room : rooms) {
            if (GroupManager.getInstance().getGroupByRoomId(room) == null)
                availableRooms.add(room);
        }
    }

    @Override
    public short getId() {
        return Composers.GroupCreationWindowMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(OrionSettings.groupCost);
        msg.writeInt(availableRooms.size());

        for (Integer room : availableRooms) {
            RoomData roomData = RoomManager.getInstance().getRoomData(room);
            if (GroupManager.getInstance().getGroupByRoomId(room) == null) {
                if(roomData == null) {
                    msg.writeInt(room);
                    msg.writeString("Unavailable");
                    msg.writeBoolean(false);
                } else {
                    msg.writeInt(roomData.getId());
                    msg.writeString(roomData.getName());
                    msg.writeBoolean(false);
                }
            }
        }

        // TODO: Stop hardcoding this
        msg.writeInt(5);
        msg.writeInt(10);
        msg.writeInt(3);
        msg.writeInt(4);
        msg.writeInt(0x19);
        msg.writeInt(0x11);
        msg.writeInt(5);
        msg.writeInt(0x19);
        msg.writeInt(0x11);
        msg.writeInt(3);
        msg.writeInt(0x1d);
        msg.writeInt(11);
        msg.writeInt(4);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
    }

    @Override
    public void dispose() {
        this.availableRooms.clear();
    }
}
