package com.orionemu.server.game.commands.staff;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.sessions.Session;


public class RemoveBadgeCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 2)
            return;

        Session session = NetworkManager.getInstance().getSessions().getByPlayerUsername(params[0]);

        if (session != null)
            session.getPlayer().getInventory().removeBadge(params[1], true);
    }

    @Override
    public String getPermission() {
        return "removebadge_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username" + " " + "command.parameter.badge", "%username% %badge%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.removebadge.description");
    }
}
