package com.orionemu.server.network.messages.outgoing.navigator;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.types.RoomData;
import com.orionemu.server.game.rooms.types.RoomWriter;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.*;
import java.util.List;


public class NavigatorFlatListMessageComposer extends MessageComposer {
    private final int category;
    private final int mode;
    private final String query;
    private final Collection<RoomData> activeRooms;
    private final boolean limit;

    public NavigatorFlatListMessageComposer(int category, final int mode, final String query, final Collection<RoomData> activeRooms, final boolean limit) {
        this.category = category;
        this.mode = mode;
        this.query = query;
        this.activeRooms = activeRooms;
        this.limit = limit;
    }


    @Override
    public short getId() {
        return Composers.NavigatorFlatListMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(mode);
        msg.writeString(query);
        msg.writeInt(limit ? (activeRooms.size() > 50 ? 50 : activeRooms.size()) : activeRooms.size());


         Collections.sort((List<RoomData>) activeRooms, new Comparator<RoomData>() {
         @Override
         public int compare(RoomData o1, RoomData o2) {
             boolean is1Active = RoomManager.getInstance().isActive(o1.getId());
             boolean is2Active = RoomManager.getInstance().isActive(o2.getId());
             return ((!is2Active ? 0 : RoomManager.getInstance().get(o2.getId()).getEntities().playerCount()) -
                     (!is1Active ? 0 : RoomManager.getInstance().get(o1.getId()).getEntities().playerCount()));
         }
                });

        List<RoomData> topRooms = new ArrayList<>();

        for (RoomData room : activeRooms) {
            if (topRooms.size() < 50 || !limit)
                topRooms.add(room);
        }

        for (RoomData room : topRooms) {
            RoomWriter.write(room, msg);
        }

        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeBoolean(false);

        // Clear the top rooms
        topRooms.clear();
        activeRooms.clear();
    }

    public NavigatorFlatListMessageComposer(int category, int mode, String query, Collection<RoomData> activeRooms) {
        this(category, mode, query, activeRooms, true);
    }
}
