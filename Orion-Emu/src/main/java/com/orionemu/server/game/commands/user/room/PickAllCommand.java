package com.orionemu.server.game.commands.user.room;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.rooms.objects.items.RoomItem;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.objects.items.RoomItemWall;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.sessions.Session;

import java.util.ArrayList;
import java.util.List;


public class PickAllCommand extends ChatCommand {
    @Override
    public void execute(Session client, String message[]) {
        Room room = client.getPlayer().getEntity().getRoom();

//        if (room == null || !room.getData().getOwner().equals(client.getPlayer().getData().getUsername())) {
//            sendNotif(Locale.getOrDefault("command.need.rights", "You have no rights to use this command in this room."), client);
//            return;
//        }

        List<RoomItem> itemsToRemove = new ArrayList<>();

        itemsToRemove.addAll(room.getItems().getFloorItems().values());
        itemsToRemove.addAll(room.getItems().getWallItems().values());

        for (RoomItem item : itemsToRemove) {
            if (item instanceof RoomItemFloor && item.getOwner() == client.getPlayer().getId()) {
                room.getItems().removeItem((RoomItemFloor) item, client);
            } else if (item instanceof RoomItemWall && item.getOwner() == client.getPlayer().getId()) {
                room.getItems().removeItem((RoomItemWall) item, client, true);
            }
        }

        itemsToRemove.clear();
    }

    @Override
    public String getPermission() {
        return "pickall_command";
    }
    
    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.pickall.description");
    }
}