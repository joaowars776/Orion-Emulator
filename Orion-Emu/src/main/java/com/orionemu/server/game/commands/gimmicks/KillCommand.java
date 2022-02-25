package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.rooms.objects.entities.RoomEntityStatus;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.sessions.Session;


public class KillCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 1) {
            sendNotif(Locale.getOrDefault("command.kick.none", "Who do you want to kill?"), client);
            return;
        }

        String username = params[0];
        Session user = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);

        if (user == null) {
            sendNotif(Locale.getOrDefault("command.user.offline", "This user is offline!"), client);
            return;
        }

        if (username.equals(client.getPlayer().getData().getUsername())) {
            return;
        }

        if (user.getPlayer().getEntity() == null) {
            sendNotif(Locale.getOrDefault("command.user.notinroom", "This user is not in a room."), client);
            return;
        }

        if (!user.getPlayer().getPermissions().getRank().roomKickable()) {
            sendNotif(Locale.getOrDefault("command.kick.unkickable", "You can't kill this player!"), client);
            return;
        }

        user.getPlayer().getEntity().addStatus(RoomEntityStatus.LAY, "0.5");
        isExecuted(client);
    }

    @Override
    public String getPermission() {
        return "kill_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username", "%username%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.kill.description");
    }
}
