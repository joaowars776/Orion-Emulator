package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.orionemu.server.network.sessions.Session;


public class NaCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] message) {
        if(Orion.getTime() < client.getPlayer().getData().getLastUseNa() + 3600) {
            sendNotif(Locale.getOrDefault("command.user.nal", "You have to wait 3600 seconds."), client);
return;
        }

        if (message.length == 0) {
            return;
        }

        NetworkManager.getInstance().getSessions().broadcast(new NotificationMessageComposer(Locale.get("get.avatar"), this.merge(message) + "\r\r - " + client.getPlayer().getData().getUsername() + ""));
        client.getPlayer().getData().setLastUseNa(Orion.getTime());
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public String getPermission() {
        return "nal_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.message", "%message%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.nal.description");
    }
}
