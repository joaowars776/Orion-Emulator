package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;

import java.util.Timer;
import java.util.TimerTask;


public class RaceCommand extends ChatCommand {
    private Timer timer = new Timer();
    @Override
    public void execute(Session client, String[] params) {
        if (client.getPlayer().getEntity() != null && client.getPlayer().getEntity().getRoom() != null) {
            if (!client.getPlayer().getEntity().getRoom().getRights().hasRights(client.getPlayer().getId()) &&
                    !client.getPlayer().getPermissions().getRank().roomFullControl()) {
                sendNotif(Locale.getOrDefault("command.need.rights", "You need rights to use this command in this room!"), client);
                return;
            }
        }
        client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* 3... *", 5));
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* 2... *", 5));

            }
        }, 1500);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* 1... *", 5));

            }
        }, 3000);

        int effectId = 196;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (PlayerEntity entity : client.getPlayer().getEntity().getRoom().getEntities().getPlayerEntities()) {
                    entity.applyEffect(new PlayerEffect(effectId, 0));
                    client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* GOOOOOOOOOOO!!! *", 5));
                }
            }
        }, 4500);
    }

    @Override
    public String getPermission() {
        return "race_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.message", "%message%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.race.description");
    }
}
