package com.orionemu.server.network.messages.incoming.room.action;

import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.avatar.TalkMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.engine.UserNameChangeMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.details.UpdateUsernameMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.google.common.primitives.Ints;


public class TalkMessageEvent implements Event {
/*
Fixar denna sen :DD
    public void tags(Session client) {
        String hex = "";
        String tag = "";
        if(client.getPlayer().getData().getRank() >= 1) {
            switch(client.getPlayer().getData().getRank()) {
                case 2:
                    hex = "#087246";
                    tag = "VIP";
                    break;
                case 3:
                    tag = "Silver";
                    hex = "#4e5451";
                    break;
                case 4:
                    tag = "Guld";
                    hex = "#8c6519";
                    break;
                case 5:
                    tag = "Eventmanager";
                    hex = "#791287";
                    break;
                case 6:
                    tag = "MOD";
                    hex = "#a5082a";
                    break;
                case 7:
                    tag = "Super-MOD";
                    hex = "#38030e";
                    break;
                case 8:
                    tag = "Ägare";
                    hex = "#000";
                    break;
                case 9:
                    tag = "WHAT";
                    hex = "#000";
            }
            String newTag = "<font color='" + hex + "'>["+ tag +"]</font>";
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new UserNameChangeMessageComposer(client.getPlayer().getEntity().getRoom().getId(), client.getPlayer().getData().getId(), newTag));
            System.out.println("Krashar denna allt?");
            }
        }
        */

    public void handle(Session client, MessageEvent msg) {
        String message = msg.readString();
        int colour = msg.readInt();

        if (!TalkMessageEvent.isValidColour(colour, client))
            colour = 0;

        String filteredMessage = filterMessage(message);


        if (client.getPlayer().getEntity().onChat(filteredMessage)) {
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new TalkMessageComposer(client.getPlayer().getEntity().getId(), filteredMessage, RoomManager.getInstance().getEmotions().getEmotion(filteredMessage), colour));
        }
    }

    private static int[] allowedColours = new int[]{
            0, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 29
    };

    public static boolean isValidColour(int colour, Session client) {
        if (!Ints.contains(allowedColours, colour)) {
            return false;
        }

        if (colour == 23 && !client.getPlayer().getPermissions().hasCommand("mod_tool"))
            return false;

        return true;
    }

    public static String filterMessage(String message) {
        return message.replace((char) 13 + "", "");
    }
}