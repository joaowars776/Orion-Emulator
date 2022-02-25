package com.orionemu.server.network.messages.incoming.landing;

import com.orionemu.server.game.landing.LandingManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.landing.PromoArticlesMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class RefreshPromoArticlesMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.send(new PromoArticlesMessageComposer(LandingManager.getInstance().getArticles()));
    }
}
