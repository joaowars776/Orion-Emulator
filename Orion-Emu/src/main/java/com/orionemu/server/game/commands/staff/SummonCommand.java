package com.orionemu.server.game.commands.staff;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.engine.RoomForwardMessageComposer;
import com.orionemu.server.network.sessions.Session;

public class SummonCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendNotif(Locale.getOrDefault("command.summon.none", "Who do you want to follow you?"), client);
            return;
        }

        final String username = params[0];

        if (!PlayerManager.getInstance().isOnline(username)) {
            sendNotif(Locale.getOrDefault("command.user.offline", "This user is offline!"), client);
            return;
        }

        Session session = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);

        if (session == null) {
            sendNotif(Locale.getOrDefault("command.user.offline", "This user is offline!"), client);
            return;
        }

        session.send(new AlertMessageComposer(Locale.get("command.summon.summoned").replace("%summoner%", client.getPlayer().getData().getUsername())));
        session.send(new RoomForwardMessageComposer(client.getPlayer().getEntity().getRoom().getId()));

        session.getPlayer().bypassRoomAuth(true);
        isExecuted(client);
    }

    @Override
    public String getPermission() {
        return "summon_command";
    }
    
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username", "%username%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.summon.description");
    }
}
