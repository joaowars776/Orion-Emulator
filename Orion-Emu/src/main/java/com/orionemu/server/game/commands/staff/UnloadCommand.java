package com.orionemu.server.game.commands.staff;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.sessions.Session;


public class UnloadCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (client.getPlayer().getEntity() != null && client.getPlayer().getEntity().getRoom() != null) {
            if (!client.getPlayer().getEntity().getRoom().getRights().hasRights(client.getPlayer().getId()) &&
                    !client.getPlayer().getPermissions().getRank().roomFullControl()) {
                sendNotif(Locale.getOrDefault("command.need.rights", "Du kan endast använda unload i rum där du har rättis!"), client);
                return;
            }
        }
        client.getPlayer().getEntity().getRoom().getItems().commit();
        client.getPlayer().getEntity().getRoom().setIdleNow();
    }

    @Override
    public String getPermission() {
        return "unload_command";
    }

    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.unload.description");
    }
}
