package com.orionemu.server.game.commands.staff.rewards.mass;

import com.orionemu.api.networking.sessions.BaseSession;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.players.types.Player;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.inventory.InventoryDao;
import com.google.common.collect.Lists;

import java.util.List;


public class MassBadgeCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 1)
            return;

        final String badgeCode = params[0];
        List<Integer> playersToInsertBadge = Lists.newArrayList();

        for (BaseSession session : NetworkManager.getInstance().getSessions().getSessions().values()) {
            try {
                ((Player) session.getPlayer()).getInventory().addBadge(badgeCode, false);
                playersToInsertBadge.add(session.getPlayer().getId());
            } catch (Exception ignored) {

            }
        }

        InventoryDao.addBadges(badgeCode, playersToInsertBadge);
        playersToInsertBadge.clear();
    }

    @Override
    public String getPermission() {
        return "massbadge_command";
    }
    
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.badge", "%badge%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.massbadge.description");
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
