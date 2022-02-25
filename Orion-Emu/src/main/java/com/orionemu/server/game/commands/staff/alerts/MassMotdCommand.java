package com.orionemu.server.game.commands.staff.alerts;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.notification.MotdNotificationMessageComposer;
import com.orionemu.server.network.sessions.Session;


public class MassMotdCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] message) {
        final MotdNotificationMessageComposer msg = new MotdNotificationMessageComposer(this.merge(message) + "\n\n- " + client.getPlayer().getData().getUsername());

        NetworkManager.getInstance().getSessions().broadcast(msg);
    }

    @Override
    public String getPermission() {
        return "massmotd_command";
    }
    
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.message", "%message%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.massmotd.description");
    }
}
