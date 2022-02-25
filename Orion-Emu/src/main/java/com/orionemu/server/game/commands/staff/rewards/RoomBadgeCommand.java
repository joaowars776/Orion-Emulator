package com.orionemu.server.game.commands.staff.rewards;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.network.sessions.Session;

public class RoomBadgeCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            return;
        }

        final String badge = params[0];

        for (PlayerEntity playerEntity : client.getPlayer().getEntity().getRoom().getEntities().getPlayerEntities()) {
            playerEntity.getPlayer().getInventory().addBadge(badge, true);
        }
    }

    @Override
    public String getPermission() {
        return "roombadge_command";
    }
    
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.badge", "%badge%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.roombadge.description");
    }
}
