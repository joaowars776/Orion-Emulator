package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;

public class BackCommand extends ChatCommand {
    private RoomManager roomManager;
    @Override
    public void execute(Session client, String[] params) {
        int effectId = 0;
        PlayerEntity entity = client.getPlayer().getEntity();
        try {
            entity.applyEffect(new PlayerEffect(0, 0));
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* Jag är tillbaka! *", 0));

        } catch (Exception e) {
            sendNotif(Locale.getOrDefault("command.brb.invalid", ":brb"), client);
        }
    }

    @Override
    public String getPermission() {
        return "back_command";
    }

    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return Locale.getOrDefault("command.back.description", "- You say brb");
    }

    @Override
    public boolean canDisable() {
        return true;
    }
}