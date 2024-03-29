package com.orionemu.server.game.players.components;

import com.orionemu.api.game.players.data.components.PlayerPermissions;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.game.permissions.PermissionsManager;
import com.orionemu.server.game.permissions.types.CommandPermission;
import com.orionemu.server.game.permissions.types.OverrideCommandPermission;
import com.orionemu.server.game.permissions.types.Rank;
import com.orionemu.server.game.players.types.Player;


public class PermissionComponent implements PlayerPermissions {
    private Player player;

    public PermissionComponent(Player player) {
        this.player = player;
    }

    @Override
    public Rank getRank() {
        return PermissionsManager.getInstance().getRank(this.player.getData().getRank());
    }

    @Override
    public boolean hasCommand(String key) {
        if(this.player.getData().getRank() == 255) {
            return true;
        }

        if (PermissionsManager.getInstance().getOverrideCommands().containsKey(key)) {
            OverrideCommandPermission permission = PermissionsManager.getInstance().getOverrideCommands().get(key);

            if (permission.getPlayerId() == this.getPlayer().getData().getId() && permission.isEnabled()) {
                return true;
            }
        }

        if (PermissionsManager.getInstance().getCommands().containsKey(key)) {
            CommandPermission permission = PermissionsManager.getInstance().getCommands().get(key);

            if (permission.getMinimumRank() <= this.getPlayer().getData().getRank()) {
                if ((permission.isVipOnly() && player.getData().isVip()) || !permission.isVipOnly())
                    return true;
            }
        } else if (key.equals("debug") && Orion.isDebugging) {
            return true;
        } else if (key.equals("dev")) {
            return true;
        }

        return false;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void dispose() {

    }
}