package com.orionemu.server.game.commands.user.room;

import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.outgoing.room.engine.RoomDataMessageComposer;
import com.orionemu.server.network.sessions.Session;

public class SetMaxCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendNotif(Locale.get("command.setmax.invalid"), client);
            return;
        }

        final Room room = client.getPlayer().getEntity().getRoom();
        final boolean hasRights = room.getRights().hasRights(client.getPlayer().getId());
        final boolean isStaff = client.getPlayer().getPermissions().getRank().roomFullControl();

        if (hasRights || isStaff) {
            try {
                final int maxPlayers = Integer.parseInt(params[0]);

                if ((maxPlayers > OrionSettings.roomMaxPlayers && !isStaff) || maxPlayers < 1) {
                    sendNotif(Locale.get("command.setmax.toomany").replace("%i", OrionSettings.roomMaxPlayers + ""), client);
                    return;
                }

                room.getData().setMaxUsers(maxPlayers);
                room.getData().save();

                sendNotif(Locale.get("command.setmax.done").replace("%i", maxPlayers + ""), client);
                room.getEntities().broadcastMessage(new RoomDataMessageComposer(room));
            } catch (Exception e) {
                sendNotif(Locale.get("command.setmax.invalid"), client);
            }
        }
    }

    @Override
    public String getPermission() {
        return "setmax_command";
    }
    
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.amount", "%amount%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.setmax.description");
    }
}
