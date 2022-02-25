package com.orionemu.server.network.messages.incoming.room.item;

import com.orionemu.server.game.rooms.objects.items.types.floor.football.FootballGateFloorItem;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class SaveFootballGateMessageEvent implements Event {

    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        Room room = client.getPlayer().getEntity().getRoom();

        if (room == null) {
            return;
        }
        if (!room.getRights().hasRights(client.getPlayer().getEntity().getPlayerId()) && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            client.disconnect();
            return;
        }

        int itemId = msg.readInt();

        if (room.getItems().getFloorItem(itemId) == null || !(room.getItems().getFloorItem(itemId) instanceof FootballGateFloorItem))
            return;

        FootballGateFloorItem floorItem = ((FootballGateFloorItem) room.getItems().getFloorItem(itemId));

        String gender = msg.readString().toUpperCase();
        String figure = msg.readString();

        floorItem.setFigure(gender.toUpperCase(), figure.split(";")[gender.equals("M") ? 0 : 1]);
        floorItem.saveData();

        floorItem.sendUpdate();
    }
}
