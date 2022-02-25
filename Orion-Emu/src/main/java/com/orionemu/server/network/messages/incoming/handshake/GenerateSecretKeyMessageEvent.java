package com.orionemu.server.network.messages.incoming.handshake;

import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.handshake.SecretKeyMessageComposer;
import com.orionemu.server.network.messages.protocol.codec.EncryptionDecoder;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class GenerateSecretKeyMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) {

        client.send(new SecretKeyMessageComposer("24231219992253632572058933470468103090824667747608911151318774416044820318109"));

        //client.getChannel().pipeline().addBefore("messageDecoder", "encryptionDecoder", new EncryptionDecoder(sharedKey));
    }
}
