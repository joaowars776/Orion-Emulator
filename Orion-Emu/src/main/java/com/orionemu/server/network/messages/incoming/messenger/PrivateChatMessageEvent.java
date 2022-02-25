package com.orionemu.server.network.messages.incoming.messenger;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.game.moderation.ModerationManager;
import com.orionemu.server.game.players.components.types.messenger.MessengerFriend;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.filter.FilterResult;
import com.orionemu.server.logging.LogManager;
import com.orionemu.server.logging.entries.MessengerChatLogEntry;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.messenger.InstantChatMessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;


public class PrivateChatMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int userId = msg.readInt();
        String message = msg.readString();

        final int timeMutedExpire = client.getPlayer().getData().getTimeMuted() - (int) Orion.getTime();

        if (client.getPlayer().getData().getTimeMuted() != 0) {
            if (client.getPlayer().getData().getTimeMuted() > (int) Orion.getTime()) {
                client.getPlayer().getSession().send(new AdvancedAlertMessageComposer(Locale.getOrDefault("command.mute.muted", "You are muted for violating the rules! Your mute will expire in %timeleft% seconds").replace("%timeleft%", timeMutedExpire + "")));
                return;
            }
        }


        final long time = System.currentTimeMillis();

        if (!client.getPlayer().getPermissions().getRank().floodBypass()) {
            if (time - client.getPlayer().getMessengerLastMessageTime() < 750) {
                client.getPlayer().setMessengerFloodFlag(client.getPlayer().getMessengerFloodFlag() + 1);

                if (client.getPlayer().getMessengerFloodFlag() >= 4) {
                    client.getPlayer().setMessengerFloodTime(time / 1000L + client.getPlayer().getPermissions().getRank().floodTime());
                    client.getPlayer().setMessengerFloodFlag(0);
                }
            }

            if ((time / 1000L) < client.getPlayer().getMessengerFloodTime()) {
                return;
            }

            client.getPlayer().setMessengerLastMessageTime(time);
        }

        if (!client.getPlayer().getPermissions().getRank().roomFilterBypass()) {
            FilterResult filterResult = RoomManager.getInstance().getFilter().filter(message);

            if (filterResult.isBlocked()) {
                client.send(new AdvancedAlertMessageComposer(Locale.get("game.message.blocked").replace("%s", filterResult.getMessage())));
                return;
            } else if (filterResult.wasModified()) {
                message = filterResult.getMessage();
            }
        }

        try {
            if (LogManager.ENABLED && OrionSettings.messengerLogMessages)
                LogManager.getInstance().getStore().getLogEntryContainer().put(new MessengerChatLogEntry(client.getPlayer().getId(), userId, message));
        } catch (Exception ignored) {

        }

        if (userId == Integer.MAX_VALUE && client.getPlayer().getPermissions().getRank().messengerStaffChat()) {
            for (Session player : ModerationManager.getInstance().getModerators()) {
                if (player == client) continue;
                player.send(new InstantChatMessageComposer(client.getPlayer().getData().getUsername() + ": " + message, Integer.MAX_VALUE));
            }
            return;
        }

        if(userId < 0 && OrionSettings.groupChatEnabled) {
            final int groupId = -userId;
            final Group group = GroupManager.getInstance().get(groupId);

            if(group != null && client.getPlayer().getGroups().contains(groupId)) {
                group.getMembershipComponent().broadcastMessage(new InstantChatMessageComposer(message, userId, client.getPlayer().getData().getUsername(), client.getPlayer().getData().getFigure(), client.getPlayer().getId()), client.getPlayer().getId());
            }

            return;
        }

        MessengerFriend friend = client.getPlayer().getMessenger().getFriendById(userId);

        if (friend == null) {
            return;
        }

        Session friendClient = friend.getSession();

        if (friendClient == null) {
            return;
        }

        friendClient.send(new InstantChatMessageComposer(message, client.getPlayer().getId()));
    }
}