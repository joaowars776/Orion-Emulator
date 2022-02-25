package com.orionemu.server.network.messages.incoming.user.details;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.messenger.MessengerConfigMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.achievements.AchievementPointsMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.achievements.AchievementRequirementsMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.details.UserObjectMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.inventory.BadgeInventoryMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.permissions.AllowancesMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class InfoRetrieveMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        client.getPlayer().sendBalance();

        // TODO: Queue these & send all at once.
        client.send(new UserObjectMessageComposer(client.getPlayer()));
        client.send(new AllowancesMessageComposer(client.getPlayer().getData().getRank()));
//        client.send(new CitizenshipStatusMessageComposer());
        client.send(new AchievementPointsMessageComposer(client.getPlayer().getData().getAchievementPoints()));

        client.send(new MessengerConfigMessageComposer(client.getPlayer().getMessenger().getFriends(), client.getPlayer().getPermissions().hasCommand("staff_chat")));
        client.send(new BadgeInventoryMessageComposer(client.getPlayer().getInventory().getBadges()));
        client.send(new AchievementRequirementsMessageComposer());

        client.getPlayer().getMessenger().sendStatus(true, client.getPlayer().getEntity() != null);
    }
}
