package com.orionemu.server.network.messages.incoming.room.item;

import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class SaveBrandingMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int brandingId = msg.readInt();

        Room room = client.getPlayer().getEntity().getRoom();

        if (room == null || (!room.getRights().hasRights(client.getPlayer().getId()) && !client.getPlayer().getPermissions().getRank().roomFullControl())) {
            return;
        }

        int length = msg.readInt();
        String data = "state" + (char) 9 + "0";

        for (int i = 1; i <= length; i++) {
            data = data + (char) 9 + msg.readString();
        }

        data = data.replace("https", "http");

        RoomItemFloor item = room.getItems().getFloorItem(brandingId);
        item.setExtraData(data);

        item.sendUpdate();
        item.saveData();
    }
}
