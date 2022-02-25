package com.orionemu.server.game.commands.user;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.rooms.objects.entities.RoomEntityStatus;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.network.sessions.Session;


public class LayCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        PlayerEntity playerEntity = client.getPlayer().getEntity();
        if (playerEntity.hasStatus(RoomEntityStatus.LAY)) {
            playerEntity.removeStatus(RoomEntityStatus.LAY);
            playerEntity.markNeedsUpdate();
            isExecuted(client);
        } else {
            playerEntity.addStatus(RoomEntityStatus.LAY, "0.5");
            playerEntity.markNeedsUpdate();
            isExecuted(client);
        }
    }

    @Override
    public String getPermission() {
        return "lay_command";
    }
    
    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.lay.description");
    }

    @Override
    public boolean canDisable() {
        return true;
    }
}
