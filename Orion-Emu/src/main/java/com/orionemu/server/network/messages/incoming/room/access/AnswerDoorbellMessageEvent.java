package com.orionemu.server.network.messages.incoming.room.access;

import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.access.DoorbellAcceptedComposer;
import com.orionemu.server.network.messages.outgoing.room.alerts.DoorbellNoAnswerComposer;
import com.orionemu.server.network.messages.outgoing.room.engine.HotelViewMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class AnswerDoorbellMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if (client.getPlayer().getEntity() == null) {
            return;
        }

        if (client.getPlayer().getEntity().getRoom() == null) {
            return;
        }

        String username = msg.readString();
        boolean answered = msg.readBoolean();

        if (username.equals("")) {
            return;
        }

        Session requestingClient = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);

        // player could of d/c
        if (requestingClient == null) {
            return;
        }

        if (requestingClient.getPlayer() == null || requestingClient.getPlayer().getEntity() == null) {
            return;
        }

        // already answered ?
        if (requestingClient.getPlayer().getEntity().isDoorbellAnswered()) {
            return;
        }

        // still ringing for this room ?
        if (requestingClient.getPlayer().getEntity().getRoom().getId() != client.getPlayer().getEntity().getRoom().getId()) {
            return;
        }

        if (!client.getPlayer().getEntity().getRoom().getRights().hasRights(client.getPlayer().getId()) && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            return;
        }

        if (answered) {
            requestingClient.getPlayer().getEntity().setDoorbellAnswered(true);
            requestingClient.send(new DoorbellAcceptedComposer());
        } else {
            requestingClient.send(new DoorbellNoAnswerComposer());
            requestingClient.send(new HotelViewMessageComposer());
        }
    }
}
