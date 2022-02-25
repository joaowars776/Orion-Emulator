package com.orionemu.server.network.messages.incoming.catalog.pets;

import com.orionemu.server.game.pets.PetManager;
import com.orionemu.server.game.pets.races.PetRace;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.catalog.pets.PetRacesMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import org.apache.commons.lang.StringUtils;

import java.util.List;


public class PetRacesMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) {
        final String petRace = msg.readString();
        final String[] splitRace = petRace.split("a0 pet");

        if (splitRace.length < 2 || !StringUtils.isNumeric(splitRace[1])) {
            return;
        }

        int raceId = Integer.parseInt(splitRace[1]);
        List<PetRace> races = PetManager.getInstance().getRacesByRaceId(raceId);

        client.send(new PetRacesMessageComposer(petRace, races));
    }
}
