package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.utilities.RandomInteger;


public class TofflaCommand extends ChatCommand {
    private final static String[] objects = {
            "en stor fisk",
            "en toffla",
            "en huggorm",
            "en tiger",
    };

    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendNotif(Locale.getOrDefault("command.user.invalid", "Invalid username!"), client);
            return;
        }

        String slappedPlayer = params[0];
        String object = objects[RandomInteger.getRandom(0, objects.length - 1)].replace("%g", client.getPlayer().getData().getGender().toLowerCase().equals("m") ? "his" : "her");

        client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* " + client.getPlayer().getData().getUsername() + " tofflar " + slappedPlayer + " med " + object + " *", 34));
    }

    @Override
    public String getPermission() {
        return "toffla_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username", "%username%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.toffla.description");
    }
}
