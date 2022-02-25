package com.orionemu.server.game.commands.staff.muting;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.PlayerDao;


public class UnmuteCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendNotif(Locale.getOrDefault("command.unmute.none", "Who do you want to unmute?"), client);
            return;
        }

        int playerId = PlayerManager.getInstance().getPlayerIdByUsername(params[0]);
        Session session = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

        if (session == null) {
            sendNotif(Locale.getOrDefault("command.user.offline", "This user is offline!"), client);
            return;
        }

        if (playerId != -1) {
            final int timeMuted = 0;

            session.send(new AdvancedAlertMessageComposer(Locale.get("command.unmute.unmuted")));

            if (session.getPlayer().getData().getTimeMuted() > (int) Orion.getTime()) {
                PlayerDao.addTimeMute(playerId, timeMuted);
                session.getPlayer().getData().setTimeMuted(timeMuted);
                isExecuted(client);
            } else {
                PlayerEntity entity = session.getPlayer().getEntity();

                if (entity != null && entity.isRoomMuted()) {
                    entity.setRoomMuted(false);
                }
                isExecuted(client);
            }
        }
    }

    @Override
    public String getPermission() {
        return "unmute_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username", "%username%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.unmute.description");
    }

    @Override
    public boolean bypassFilter() {
        return true;
    }
}