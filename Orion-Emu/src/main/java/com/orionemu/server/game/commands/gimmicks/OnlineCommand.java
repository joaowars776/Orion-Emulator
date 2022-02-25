package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.api.stats.OrionStats;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;

/**
 * Created by Emrik on 2017-08-09.
 */
public class OnlineCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        OrionStats orionStats = Orion.getStats();
        int OnlineCount = orionStats.getPlayers() + 3;
        if(params.length < 1) {
            client.send(new WhisperMessageComposer(client.getPlayer().getId(), "" + OnlineCount + " spelare online!", 20));
        }
    }

    @Override
    public String getPermission() {
        return "online_command";
    }

    @Override
    public String getParameter() { return  Locale.getOrDefault("command.parameter.message", "Visa antal spelare online"); }

    @Override
    public String getDescription() {
        { return Locale.get("command.about.description"); }
    }
}
