package com.orionemu.server.game.commands.user;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.messages.outgoing.room.engine.RoomForwardMessageComposer;
import com.orionemu.server.network.sessions.Session;

public class HomeCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (client.getPlayer().getSettings().getHomeRoom() > 0) {
            client.send(new RoomForwardMessageComposer(client.getPlayer().getSettings().getHomeRoom()));
        }
    }

    @Override
    public String getPermission() {
        return "home_command";
    }
    
    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.home.description");
    }
}
