package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;

public class LiraCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length == 0) {

            if(!client.getPlayer().getData().getLira()){
            client.getPlayer().getData().setLira(true);
            client.send(new WhisperMessageComposer(client.getPlayer().getId(), "Du har nu aktiverat lira kommandot", 34 ));
            } else {
                client.getPlayer().getData().setLira(false);
                client.send(new WhisperMessageComposer(client.getPlayer().getId(), "Du har nu inaktiverat lira kommandot", 34 ));
            }
        }
    }

    @Override
    public String getPermission() {
        return "lira_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.number", "%number%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.lira.description");
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
