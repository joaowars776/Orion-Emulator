package com.orionemu.server.game.commands.staff.rewards;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.PlayerDao;
import com.orionemu.server.storage.queries.player.inventory.InventoryDao;


public class GiveBadgeCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 2)
            return;

        final String username = params[0];
        final String badge = params[1];

        Session session = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);

        if (session != null) {
            session.getPlayer().getInventory().addBadge(badge, true);
            sendNotif(Locale.get("command.givebadge.success").replace("%username%", username).replace("%badge%", badge), client);
        } else {
            int playerId = PlayerDao.getIdByUsername(username);

            if(playerId == 0) {
                sendNotif(Locale.get("command.givebadge.fail").replace("%username%", username).replace("%badge%", badge), client);
            } else {
                InventoryDao.addBadge(badge, playerId);
                sendNotif(Locale.get("command.givebadge.success").replace("%username%", username).replace("%badge%", badge), client);
            }
        }
    }

    @Override
    public String getPermission() {
        return "givebadge_command";
    }
    
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username" + " " + "command.parameter.badge", "%username% %badge%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.givebadge.description");
    }
}
