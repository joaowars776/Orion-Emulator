package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;

import java.util.Timer;
import java.util.TimerTask;

public class WeedCommand extends ChatCommand {
    private RoomManager roomManager;
    private Timer timer = new Timer();
    @Override
    public void execute(Session client, String[] params) {
        try {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* Tänder en gås *", 1));

                }
            }, 0000);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* Wow, det är detta jag level för! *", 0));

                }
            }, 2000);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* Står jag på en regnbåge?? *", 0));

                }
            }, 6000);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* Tar ett till bloss *", 1));

                }
            }, 10000);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* Wow man, detta var nåt nytt.. *", 0));

                }
            }, 14000);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* Börjar känna mig yr... *", 0));

                }
            }, 18000);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* Ta detta kommando med en klackspark, vi uppmanar inte droger!  *", 32));

                }
            }, 24000);



        } catch (Exception e) {
            sendNotif(Locale.getOrDefault("command.weed.invalid", ":weed"), client);
        }
    }

    @Override
    public String getPermission() {
        return "weed_command";
    }

    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return Locale.getOrDefault("command.weed.description", "- You smoke weed.");
    }

    @Override
    public boolean canDisable() {
        return true;
    }
}