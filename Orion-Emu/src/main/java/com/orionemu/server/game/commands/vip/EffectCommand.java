package com.orionemu.server.game.commands.vip;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.permissions.PermissionsManager;
import com.orionemu.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.types.components.games.GameTeam;
import com.orionemu.server.network.sessions.Session;


public class EffectCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendNotif(Locale.getOrDefault("command.enable.none", "To get a effect type :effect %number%"), client);
            return;
        }

        try {
            int effectId = Integer.parseInt(params[0]);

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
        return "enable_command";
    }
    
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.number", "%number%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.enable.description");
    }

    @Override
    public boolean canDisable() {
        return true;
    }
}
