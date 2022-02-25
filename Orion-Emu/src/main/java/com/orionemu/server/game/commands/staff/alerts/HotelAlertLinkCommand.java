package com.orionemu.server.game.commands.staff.alerts;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.sessions.Session;

public class HotelAlertLinkCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 2) {
            sendNotif(Locale.getOrDefault("command.hotelalertlink.args", "This command requires at least 2 arguments!"), client);
        }

        final String link = params[0];

        NetworkManager.getInstance().getSessions().broadcast(new AdvancedAlertMessageComposer(Locale.getOrDefault("command.hotelalertlink.title", "Alert"), this.merge(params, 1) + "<br><br><i> " + client.getPlayer().getData().getUsername() + "</i>", Locale.getOrDefault("command.hotelalertlink.buttontitle", "Click here"), link, ""));
    }

    @Override
    public String getPermission() {
        return "hotelalertlink_command";
    }
    
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.message", "%message%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.hotelalertlink.description");
    }
}
