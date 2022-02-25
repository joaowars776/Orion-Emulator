package com.orionemu.server.network.messages.incoming.help;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.help.SanctionStatusComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class GetSanctionStatusEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        client.send(new SanctionStatusComposer());
    }
}
