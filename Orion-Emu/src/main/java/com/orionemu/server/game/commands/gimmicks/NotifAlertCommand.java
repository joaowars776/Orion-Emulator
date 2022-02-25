package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.orionemu.server.network.sessions.Session;


public class NotifAlertCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] message) {
        if (message.length == 0) {
            return;
        }

        NetworkManager.getInstance().getSessions().broadcast(new NotificationMessageComposer(Locale.get("generic"), this.merge(message) + "\r\r - " + client.getPlayer().getData().getUsername() + ""));
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public String getPermission() {
        return "notifalert_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.message", "%message%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.notifalert.description");
    }
}