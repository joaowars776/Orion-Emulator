package com.orionemu.server.network.messages.incoming.messenger;

import com.orionemu.server.game.achievements.types.AchievementType;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.messenger.BuddyListMessageComposer;
import com.orionemu.server.network.messages.outgoing.messenger.FriendRequestsMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

public class InitializeFriendListMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.send(new BuddyListMessageComposer(client.getPlayer(), client.getPlayer().getMessenger().getFriends(), client.getPlayer().getPermissions().getRank().messengerStaffChat(), client.getPlayer().getGroups()));
        client.send(new FriendRequestsMessageComposer(client.getPlayer().getMessenger().getRequestAvatars()));

        if(!client.getPlayer().getAchievements().hasStartedAchievement(AchievementType.FRIENDS_LIST)) {
            client.getPlayer().getAchievements().progressAchievement(AchievementType.FRIENDS_LIST, client.getPlayer().getMessenger().getFriends().size());
        }
        
        client.getPlayer().getMessenger().setInitialised(true);
    }
}