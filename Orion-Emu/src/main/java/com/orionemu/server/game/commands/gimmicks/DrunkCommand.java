package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;

public class DrunkCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        try
        {
            int handItem = 24;
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* Wow, this whine just tastes awesome! *", 1));
            client.getPlayer().getEntity().carryItem(handItem, false);
        } catch (Exception e) {
            sendNotif(Locale.getOrDefault("command.drunk.invalid", ":drunk"), client);
        }
    }

    @Override
    public String getPermission() {
        return Locale.get("drunk_command");
    }

    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.drunk.description");
    }
}
