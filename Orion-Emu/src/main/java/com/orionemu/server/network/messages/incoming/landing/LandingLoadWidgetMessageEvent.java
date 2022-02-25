package com.orionemu.server.network.messages.incoming.landing;

import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.game.landing.LandingManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.landing.HotelViewItemMessageComposer;
import com.orionemu.server.network.messages.outgoing.landing.SendHotelViewLooksMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class LandingLoadWidgetMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final String text = msg.readString();
        final String[] splitText = text.split(",");

        client.getPlayer().getData().setInHotelView(true);
        if (text.isEmpty() || splitText.length < 2) {
            client.send(new HotelViewItemMessageComposer("", ""));

            if(OrionSettings.hallOfFameEnabled) {
                client.send(new SendHotelViewLooksMessageComposer(OrionSettings.hallOfFameTextsKey, LandingManager.getInstance().getHallOfFame()));
            }

            return;
        }

        if (splitText[1].equals("gamesmaker")) return;

        client.send(new HotelViewItemMessageComposer(text, splitText[1]));

        if(OrionSettings.hallOfFameEnabled) {
            client.send(new SendHotelViewLooksMessageComposer(OrionSettings.hallOfFameTextsKey, LandingManager.getInstance().getHallOfFame()));
        }
    }
}
