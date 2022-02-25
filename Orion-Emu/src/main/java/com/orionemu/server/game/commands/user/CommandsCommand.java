package com.orionemu.server.game.commands.user;

import com.orionemu.api.commands.CommandInfo;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.commands.CommandManager;
import com.orionemu.server.modules.ModuleManager;
import com.orionemu.server.network.messages.outgoing.notification.MotdNotificationMessageComposer;
import com.orionemu.server.network.sessions.Session;

import java.util.Map;


public class CommandsCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        StringBuilder list = new StringBuilder();

        for (Map.Entry<String, CommandInfo> commandInfoEntry : ModuleManager.getInstance().getEventHandler().getCommands().entrySet()) {
            if (client.getPlayer().getPermissions().hasCommand(commandInfoEntry.getValue().getPermission()) || commandInfoEntry.getValue().getPermission().isEmpty()) {
                list.append(commandInfoEntry.getKey() + " - " + commandInfoEntry.getValue().getDescription() + "\n");
            }
        }

        for (Map.Entry<String, ChatCommand> command : CommandManager.getInstance().getChatCommands().entrySet()) {
            if (command.getValue().isHidden()) continue;

            if (client.getPlayer().getPermissions().hasCommand(command.getValue().getPermission())) {
                list.append(command.getKey().split(",")[0] + " " + command.getValue().getParameter() + " " + command.getValue().getDescription() + "\r\n");
            }
        }

        client.send(new MotdNotificationMessageComposer("Tillgängliga kommandon:\r\r" + list.toString()));
    }

    @Override
    public String getPermission() {
        return "commands_command";
    }

    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.commands.description");
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
