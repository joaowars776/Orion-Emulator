package com.orionemu.server.game.commands.staff.alerts;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.notification.HotelWillCloseInMinutesAndBackComposer;
import com.orionemu.server.network.sessions.Session;


public class CloseCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 2) {
            sendNotif(Locale.getOrDefault("command.alert.none", "Who do you want to send a alert?"), client);
            return;
        }

        int closeInMinutes = Integer.parseInt(params[0]);
        int reOpenInMinutes = Integer.parseInt(params[1]);

        NetworkManager.getInstance().getSessions().broadcast(new HotelWillCloseInMinutesAndBackComposer(closeInMinutes, reOpenInMinutes));
    }

    @Override
    public String getPermission() {
        return "close_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.name" + " " + "command.parameter.message", "%username% %message%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.close.description");
    }
}
