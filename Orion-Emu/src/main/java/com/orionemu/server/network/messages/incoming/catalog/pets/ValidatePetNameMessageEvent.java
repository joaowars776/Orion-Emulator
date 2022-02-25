package com.orionemu.server.network.messages.incoming.catalog.pets;

import com.orionemu.server.game.pets.PetManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.catalog.pets.ValidatePetNameMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class ValidatePetNameMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        String name = msg.readString();
        int errorCode = PetManager.getInstance().validatePetName(name);
        String data = null;

        switch (errorCode) {
            case 1:
                // LONG
                // TODO: put in config
                data = "We expect a maximum of 16 characters!"; // we send the max length we expect
                break;

            case 2:
                // SHORT
                data = "16"; // we send the max length we expect
                break;
            case 3:
                // INVALID CHARACTERS
                break;

            case 4:
                //WORD FILTER
                break;
        }

        client.send(new ValidatePetNameMessageComposer(errorCode, data));
    }
}
