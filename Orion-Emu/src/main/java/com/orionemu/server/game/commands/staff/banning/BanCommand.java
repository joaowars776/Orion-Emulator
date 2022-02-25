package com.orionemu.server.game.commands.staff.banning;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.moderation.BanManager;
import com.orionemu.server.game.moderation.types.BanType;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.sessions.Session;


public class BanCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 2) {
            sendNotif(Locale.getOrDefault("command.params.length", "Oops! You did something wrong!"), client);
            return;
        }

        String username = params[0];
        int length = Integer.parseInt(params[1]);

        Session user = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);

        if (user == null) {
            sendNotif(Locale.getOrDefault("command.user.offline", "This user is offline!"), client);
            return;
        }

        if (user == client || !user.getPlayer().getPermissions().getRank().bannable()) {
            sendNotif(Locale.getOrDefault("command.user.notbannable", "You're not able to ban this user!"), client);
            return;
        }

        client.getPlayer().getStats().setBans(client.getPlayer().getStats().getBans() + 1);

        user.disconnect("banned");

        long expire = Orion.getTime() + (length * 3600);

        BanManager.getInstance().banPlayer(BanType.USER, user.getPlayer().getId() + "", length, expire, params.length > 2 ? this.merge(params, 2) : "", client.getPlayer().getId());
    }

    @Override
    public String getPermission() {
        return "ban_command";
    }
    
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.ban", "%username% %time% %reason%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.ban.description");
    }

    @Override
    public boolean bypassFilter() {
        return true;
    }
}
