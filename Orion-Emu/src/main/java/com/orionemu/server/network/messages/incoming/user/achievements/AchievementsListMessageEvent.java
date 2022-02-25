package com.orionemu.server.network.messages.incoming.user.achievements;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.user.achievements.AchievementsListMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class AchievementsListMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.send(new AchievementsListMessageComposer(client.getPlayer().getAchievements()));
    }
}
