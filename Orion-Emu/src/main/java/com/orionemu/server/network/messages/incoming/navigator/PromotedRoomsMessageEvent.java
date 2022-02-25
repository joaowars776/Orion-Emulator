package com.orionemu.server.network.messages.incoming.navigator;

import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.types.RoomData;
import com.orionemu.server.game.rooms.types.RoomPromotion;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.navigator.NavigatorFlatListMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.google.common.collect.Lists;

import java.util.List;


public class PromotedRoomsMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        List<RoomData> promotedRooms = Lists.newArrayList();

        for (RoomPromotion roomPromotion : RoomManager.getInstance().getRoomPromotions().values()) {
            if (roomPromotion != null) {
                RoomData roomData = RoomManager.getInstance().getRoomData(roomPromotion.getRoomId());

                if (roomData != null) {
                    promotedRooms.add(roomData);
                }
            }
        }


        client.send(new NavigatorFlatListMessageComposer(0, 0, "", promotedRooms));
    }
}
