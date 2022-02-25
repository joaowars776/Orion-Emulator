package com.orionemu.server.network.messages.incoming.navigator;

import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.game.rooms.types.RoomData;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.navigator.PopularTagsMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.utilities.comporators.ValueComparator;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.TreeMap;


public class LoadSearchRoomMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        Map<String, Integer> tagsPlayerCount = Maps.newHashMap();

        for (RoomData roomData : RoomManager.getInstance().getRoomsByCategory(-1)) {
            if (roomData.getTags().length != 0) {
                if (!RoomManager.getInstance().isActive(roomData.getId())) continue;

                Room room = RoomManager.getInstance().get(roomData.getId());

                for (String tag : roomData.getTags()) {
                    if (tagsPlayerCount.containsKey(tag)) {
                        tagsPlayerCount.replace(tag, tagsPlayerCount.get(tag) + room.getEntities().playerCount());
                    } else {
                        tagsPlayerCount.put(tag, room.getEntities().playerCount());
                    }
                }
            }
        }

        TreeMap<String, Integer> treeMap = new TreeMap<>(new ValueComparator(tagsPlayerCount));
        treeMap.putAll(tagsPlayerCount);

        client.send(new PopularTagsMessageComposer(treeMap));

        tagsPlayerCount.clear();
    }
}
