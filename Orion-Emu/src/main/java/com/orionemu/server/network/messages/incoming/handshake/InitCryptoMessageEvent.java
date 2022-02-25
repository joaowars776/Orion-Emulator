package com.orionemu.server.network.messages.incoming.handshake;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.handshake.InitCryptoMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class InitCryptoMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) {
        client.send(new InitCryptoMessageComposer("12f449917de4f94a8c48dbadd92b6276", ""));
    }
}
