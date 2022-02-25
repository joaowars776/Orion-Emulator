package com.orionemu.server.game.commands.staff;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.notification.EpicPopupFrameComposer;
import com.orionemu.server.network.sessions.Session;


public class EpicCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 2) {
            sendNotif(Locale.getOrDefault("command.alert.none", "Who do you want to send a alert?"), client);
            return;
        }

        String assetURL = params[0];

        NetworkManager.getInstance().getSessions().broadcast(new EpicPopupFrameComposer(assetURL));
    }

    @Override
    public String getPermission() {
        return "epic_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.name" + " " + "command.parameter.message", "%username% %message%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.epic.description");
    }
}
