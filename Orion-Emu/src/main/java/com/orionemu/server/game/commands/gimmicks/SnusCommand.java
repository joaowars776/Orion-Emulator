package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;

public class SnusCommand extends ChatCommand {
    private RoomManager roomManager;
    @Override
    public void execute(Session client, String[] params) {
        try {
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* Takes a snuff! *", 34));
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* This is terrible, it should be burning under my lip! *", 1));

        } catch (Exception e) {
            sendNotif(Locale.getOrDefault("command.snus.invalid", "*Picks up a can...*"), client);
        }
    }

    @Override
    public String getPermission() {
        return "snus_command";
    }

    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return Locale.getOrDefault("command.snus.description", "Take a snuff");
    }

    @Override
    public boolean canDisable() {
        return true;
    }
}