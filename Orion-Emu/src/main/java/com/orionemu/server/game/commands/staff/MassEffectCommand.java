package com.orionemu.server.game.commands.staff;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.permissions.PermissionsManager;
import com.orionemu.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.sessions.Session;


public class MassEffectCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendNotif(Locale.getOrDefault("command.masseffect.none", "To give everyone in the room an effect type :masseffect %number%"), client);
            return;
        }

        final Room room = client.getPlayer().getEntity().getRoom();

        if(!room.getRights().hasRights(client.getPlayer().getId())) {
            return;
        }

        try {
            int effectId = Integer.parseInt(params[0]);

            final Integer minimumRank = PermissionsManager.getInstance().getEffects().get(effectId);

            if(minimumRank != null && client.getPlayer().getData().getRank() < minimumRank) {
                effectId = 10;
            }

            for (PlayerEntity playerEntity : client.getPlayer().getEntity().getRoom().getEntities().getPlayerEntities()) {
                playerEntity.applyEffect(new PlayerEffect(effectId, 0));
            }

        } catch (Exception e) {
            sendNotif(Locale.get("command.masseffect.invalidid"), client);
        }
    }

    @Override
    public String getPermission() {
        return "masseffect_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.number", "%number%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.masseffect.description");
    }
}