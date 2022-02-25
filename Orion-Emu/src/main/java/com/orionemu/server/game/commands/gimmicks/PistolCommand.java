package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.permissions.PermissionsManager;
import com.orionemu.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.types.components.games.GameTeam;
import com.orionemu.server.network.sessions.Session;


public class PistolCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        {
        }

        try {
            int effectId = 101;

            final Integer minimumRank = PermissionsManager.getInstance().getEffects().get(effectId);

            if(minimumRank != null && client.getPlayer().getData().getRank() < minimumRank) {
                effectId = 10;
            }

            PlayerEntity entity = client.getPlayer().getEntity();

            if (entity.getCurrentEffect() != null) {
                if (entity.getGameTeam() != null && entity.getGameTeam() != GameTeam.NONE) {
                    return;
                }

                if (entity.getCurrentEffect().isItemEffect()) {
                    return;
                }
            }

            entity.applyEffect(new PlayerEffect(effectId, 0));
        } catch (Exception e) {
            sendNotif(Locale.get("command.enable.invalidid"), client);
        }
    }

    @Override
    public String getPermission() {
        return "pistol_command";
    }

    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.pistol.description");
    }

    @Override
    public boolean canDisable() {
        return true;
    }
}
