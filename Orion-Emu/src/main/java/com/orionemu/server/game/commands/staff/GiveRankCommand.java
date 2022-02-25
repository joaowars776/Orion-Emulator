package com.orionemu.server.game.commands.staff;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.sessions.Session;


public class GiveRankCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length == 0) {
            sendNotif(Locale.getOrDefault("command.kick.none", "Dum eller?"), client);
            return;
        }
        //Klart, kodat :giverank, det är inte raketforskning hahahahaha
        int rank = Integer.parseInt(params[1]);
        client.getPlayer().getData().setRank(rank);

        }

    @Override
    public String getPermission() {
        return "kick_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username", "%username%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.kick.description");
    }
}
