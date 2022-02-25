package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;

public class BrbCommand extends ChatCommand {
    private RoomManager roomManager;
    @Override
    public void execute(Session client, String[] params) {
        int effectId = 201;
        PlayerEntity entity = client.getPlayer().getEntity();
        try {
            entity.applyEffect(new PlayerEffect(effectId, 0));
            client.getPlayer().getEntity().setIdle();
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* Be right back (brb)! *", 32));
            sendNotif(Locale.getOrDefault("command.need.back", "Använd :back när du är tillbaka!"), client);

        } catch (Exception e) {
            sendNotif(Locale.getOrDefault("command.brb.invalid", ":brb"), client);
        }
    }

    @Override
    public String getPermission() {
        return "brb_command";
    }

    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return Locale.getOrDefault("command.brb.description", "- You say brb");
    }

    @Override
    public boolean canDisable() {
        return true;
    }
}