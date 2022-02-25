package com.orionemu.server.network.messages.incoming.user.profile;

import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.user.profile.RelationshipsMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.relationships.RelationshipDao;


public class GetRelationshipsMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int userId = msg.readInt();

        if (userId == client.getPlayer().getId()) {
            client.send(new RelationshipsMessageComposer(client.getPlayer().getId(), client.getPlayer().getRelationships().getRelationships()));
            return;
        }

        if (NetworkManager.getInstance().getSessions().getByPlayerId(userId) != null) {
            client.send(new RelationshipsMessageComposer(userId, NetworkManager.getInstance().getSessions().getByPlayerId(userId).getPlayer().getRelationships().getRelationships()));
            return;
        }

        client.send(new RelationshipsMessageComposer(userId, RelationshipDao.getRelationshipsByPlayerId(userId)));
    }
}
