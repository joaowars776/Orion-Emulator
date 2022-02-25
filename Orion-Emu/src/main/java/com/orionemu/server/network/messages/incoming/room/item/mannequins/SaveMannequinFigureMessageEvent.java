package com.orionemu.server.network.messages.incoming.room.item.mannequins;

import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.objects.items.types.floor.boutique.MannequinFloorItem;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.items.UpdateFloorItemMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;


public class SaveMannequinFigureMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int virtualId = msg.readInt();

        long id = ItemManager.getInstance().getItemIdByVirtualId(virtualId);

        Room room = client.getPlayer().getEntity().getRoom();

        if (room == null || !room.getRights().hasRights(client.getPlayer().getId()) && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            return;
        }

        RoomItemFloor item = room.getItems().getFloorItem(id);

        if (item == null) {
            return;
        }

        String[] figureParts = client.getPlayer().getData().getFigure().split("\\.");
        String finalFigure = "";

        for (String figurePart : figureParts) {
            if (!figurePart.contains("hr") && !figurePart.contains("hd") && !figurePart.contains("he") && !figurePart.contains("ha")) {
                finalFigure += figurePart + ".";
            }
        }

        ((MannequinFloorItem) item).setFigure(finalFigure.substring(0, finalFigure.length() - 1));
        ((MannequinFloorItem) item).setGender(client.getPlayer().getData().getGender());

        room.getEntities().broadcastMessage(new UpdateFloorItemMessageComposer(item));
        item.saveData();
    }
}
