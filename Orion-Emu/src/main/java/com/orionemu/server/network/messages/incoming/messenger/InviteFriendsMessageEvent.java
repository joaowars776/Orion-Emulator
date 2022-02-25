package com.orionemu.server.network.messages.incoming.messenger;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.filter.FilterResult;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.messenger.InviteFriendMessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

import java.util.ArrayList;
import java.util.List;


public class InviteFriendsMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final long time = System.currentTimeMillis();

        final int timeMutedExpire = client.getPlayer().getData().getTimeMuted() - (int) Orion.getTime();

        if (client.getPlayer().getData().getTimeMuted() != 0) {
            if (client.getPlayer().getData().getTimeMuted() > (int) Orion.getTime()) {
                client.getPlayer().getSession().send(new AdvancedAlertMessageComposer(Locale.getOrDefault("command.mute.muted", "You are muted for violating the rules! Your mute will expire in %timeleft% seconds").replace("%timeleft%", timeMutedExpire + "")));
                return;
            }
        }

        if (!client.getPlayer().getPermissions().getRank().floodBypass()) {
            if (time - client.getPlayer().getMessengerLastMessageTime() < 750) {
                client.getPlayer().setMessengerFloodFlag(client.getPlayer().getMessengerFloodFlag() + 1);

                if (client.getPlayer().getMessengerFloodFlag() >= 3) {
                    client.getPlayer().setMessengerFloodTime(client.getPlayer().getPermissions().getRank().floodTime());
                    client.getPlayer().setMessengerFloodFlag(0);

                }
            }

            if (client.getPlayer().getMessengerFloodTime() >= 1) {
                return;
            }

            client.getPlayer().setMessengerLastMessageTime(time);
        }

        int friendCount = msg.readInt();
        List<Integer> friends = new ArrayList<>();

        for (int i = 0; i < friendCount; i++) {
            friends.add(msg.readInt());
        }

        String message = msg.readString();

        if (!client.getPlayer().getPermissions().getRank().roomFilterBypass()) {
            FilterResult filterResult = RoomManager.getInstance().getFilter().filter(message);

            if (filterResult.isBlocked()) {
                client.send(new AdvancedAlertMessageComposer(Locale.get("game.message.blocked").replace("%s", filterResult.getMessage())));
                return;
            } else if (filterResult.wasModified()) {
                message = filterResult.getMessage();
            }
        }

        client.getPlayer().getMessenger().broadcast(friends, new InviteFriendMessageComposer(message, client.getPlayer().getId()));
        friends.clear();
    }
}