package com.orionemu.server.network.messages.incoming.room.settings;

import com.orionemu.api.game.rooms.settings.RoomBanState;
import com.orionemu.api.game.rooms.settings.RoomKickState;
import com.orionemu.api.game.rooms.settings.RoomMuteState;
import com.orionemu.api.game.rooms.settings.RoomTradeState;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.navigator.NavigatorManager;
import com.orionemu.server.game.navigator.types.Category;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.filter.FilterResult;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.game.rooms.types.RoomData;
import com.orionemu.server.game.rooms.types.RoomWriter;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.engine.RoomDataMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.settings.RoomInfoUpdatedMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.settings.RoomVisualizationSettingsMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.settings.SettingsUpdatedMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;


public class SaveRoomDataMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        Room room = client.getPlayer().getEntity().getRoom();

        if (room == null || (room.getData().getOwnerId() != client.getPlayer().getId() && !client.getPlayer().getPermissions().getRank().roomFullControl())) {
            return;
        }

        RoomData data = room.getData();

        int id = msg.readInt();
        String name = msg.readString();
        String description = msg.readString();
        int state = msg.readInt();
        String password = msg.readString();
        int maxUsers = msg.readInt();
        int categoryId = msg.readInt();
        int tagCount = msg.readInt();

        StringBuilder tagBuilder = new StringBuilder();

        for (int i = 0; i < tagCount; i++) {
            if (i > 0) {
                tagBuilder.append(",");
            }

            String tag = msg.readString();
            tagBuilder.append(tag);
        }

        String tagString = tagBuilder.toString();
        String[] tags = tagString.split(",");

        int junk = msg.readInt();

        boolean allowPets = msg.readBoolean();
        boolean allowPetsEat = msg.readBoolean();

        boolean allowWalkthrough = msg.readBoolean();
        boolean hideWall = msg.readBoolean();
        int wallThick = msg.readInt();
        int floorThick = msg.readInt();

        int whoMute = msg.readInt();
        int whoKick = msg.readInt();
        int whoBan = msg.readInt();

        if (wallThick < -2 || wallThick > 1) {
            wallThick = 0;
        }

        if (floorThick < -2 || floorThick > 1) {
            floorThick = 0;
        }

        if (name.length() < 1) {
            return;
        }

        if (state < 0 || state > 3) {
            return;
        }

        if (maxUsers < 0) {
            return;
        }

        /*if (!client.getPlayer().getPermissions().hasPermission("mod_tool") && maxUsers > CometSettings.maxPlayersInRoom) {
            return;
        }*/

        Category category = NavigatorManager.getInstance().getCategory(categoryId);

        if (category == null) {
            return;
        }

        if (category.getRequiredRank() > client.getPlayer().getData().getRank()) {
            categoryId = 15; // 15 = the uncategorized category.
        }

        if (tags.length > 2) {
            return;
        }

        data.setAccess(RoomWriter.roomAccessToString(state));
        data.setCategory(categoryId);
        data.setDescription(description);
        data.setName(name);
        data.setPassword(password);
        data.setMaxUsers(maxUsers);
        data.setTags(tags);
        data.setThicknessWall(wallThick);
        data.setThicknessFloor(floorThick);
        data.setHideWalls(hideWall);
        data.setAllowWalkthrough(allowWalkthrough);
        data.setAllowPets(allowPets);

        try {
            data.save();

            room.getEntities().broadcastMessage(new RoomVisualizationSettingsMessageComposer(hideWall, wallThick, floorThick));
            room.getEntities().broadcastMessage(new RoomDataMessageComposer(room));
        } catch (Exception e) {
            RoomManager.log.error("Error while saving room data", e);
        }
    }
}
