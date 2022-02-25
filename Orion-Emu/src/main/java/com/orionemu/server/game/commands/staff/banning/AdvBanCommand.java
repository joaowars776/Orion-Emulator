package com.orionemu.server.game.commands.staff.banning;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.moderation.BanManager;
import com.orionemu.server.game.moderation.types.BanType;
import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.sessions.Session;

import java.util.List;


public class AdvBanCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 1) {
            sendNotif(Locale.getOrDefault("command.params.length", "Oops! You did something wrong!"), client);
            return;
        }

        String username = params[0];

        Session user = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);

        if (user == null) {
            sendNotif(Locale.getOrDefault("command.user.offline", "This user is offline!"), client);
            return;
        }

        if (user == client || !user.getPlayer().getPermissions().getRank().bannable()) {
            sendNotif(Locale.getOrDefault("command.user.notbannable", "You're not able to ban this user!"), client);
            return;
        }

        long expire = Orion.getTime() + (72 * 3600);

        String ipAddress = user.getIpAddress();

        if (!BanManager.getInstance().hasBan(ipAddress, BanType.IP)) {
            BanManager.getInstance().banPlayer(BanType.IP, user.getIpAddress(), 72, expire, Locale.getOrDefault("command.advban.reason", "Advertising"), client.getPlayer().getId());
        }

        BanManager.getInstance().banPlayer(BanType.USER, user.getPlayer().getId() + "", 72, expire, Locale.getOrDefault("command.advban.reason", "Advertising"), client.getPlayer().getId());

        List<Integer> playerIds = PlayerManager.getInstance().getPlayerIdsByIpAddress(ipAddress);

        for(int playerId : playerIds) {
            Session player = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

            if(player != null) {
                player.disconnect("banned");
            }
        }

        playerIds.clear();
    }

    @Override
    public String getPermission() {
        return "advban_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.advban", "%username% %reason%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.advban.description");
    }

    @Override
    public boolean bypassFilter() {
        return true;
    }

}
