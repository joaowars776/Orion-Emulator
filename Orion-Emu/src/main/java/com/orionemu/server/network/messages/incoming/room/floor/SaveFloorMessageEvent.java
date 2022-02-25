package com.orionemu.server.network.messages.incoming.room.floor;

import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.players.types.Player;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.models.CustomFloorMapData;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.game.rooms.types.RoomReloadListener;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.engine.RoomForwardMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.utilities.JsonUtil;


public class SaveFloorMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        String model = msg.readString();

        if (client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) {
            return;
        }

        Room room = client.getPlayer().getEntity().getRoom();

        if ((room.getData().getOwnerId() != client.getPlayer().getId() && !client.getPlayer().getPermissions().getRank().roomFullControl())) {
            return;
        }

        model = model.replace((char) 10, (char) 0);
        String[] modelData = model.split(String.valueOf((char) 13));

        int sizeY = modelData.length;
        int sizeX = modelData[0].length();

        if (sizeX < 2 || sizeY < 2 || (OrionSettings.floorEditorMaxX != 0 && sizeX > OrionSettings.floorEditorMaxX) || (OrionSettings.floorEditorMaxY != 0 && sizeY > OrionSettings.floorEditorMaxY) || (OrionSettings.floorEditorMaxTotal != 0 && OrionSettings.floorEditorMaxTotal < (sizeX * sizeY))) {
            client.send(new AdvancedAlertMessageComposer("Invalid Model", Locale.get("command.floor.size")));
            return;
        }

        int lastLineLength = 0;
        boolean isValid = true;
        try {
            for (int i = 0; i < modelData.length; i++) {
                if (lastLineLength == 0) {
                    lastLineLength = modelData[i].length();
                    continue;
                }

                if (lastLineLength != modelData[i].length()) {
                    isValid = false;
                }
            }
        } catch (Exception e) {
            client.send(new AdvancedAlertMessageComposer("Invalid Model", "There seems to be a problem parsing this floor plan, please either try again or contact an admin!"));
        }

        if (!isValid) {
            client.send(new AdvancedAlertMessageComposer("Invalid Model", Locale.get("command.floor.invalid")));
            return;
        }

        final CustomFloorMapData floorMapData = new CustomFloorMapData(room.getModel().getDoorX(), room.getModel().getDoorY(), room.getModel().getDoorRotation(), model.trim(), -1);

        room.getData().setHeightmap(JsonUtil.getInstance().toJson(floorMapData));
        room.getData().save();

//        client.send(new AdvancedAlertMessageComposer("Model Saved", Locale.get("command.floor.complete"), "Go", "event:navigator/goto/" + client.getPlayer().getEntity().getRoom().getId(), ""));

        room.getItems().commit();

        final RoomReloadListener reloadListener = new RoomReloadListener(room, (players, newRoom) -> {
           for(Player player : players) {
               if(player.getEntity() == null) {
                   player.getSession().send(new AlertMessageComposer(Locale.get("command.floor.complete")));
                   player.getSession().send(new RoomForwardMessageComposer(newRoom.getId()));
               }
           }
        });

        RoomManager.getInstance().addReloadListener(room.getId(), reloadListener);
        room.reload();
    }
}
