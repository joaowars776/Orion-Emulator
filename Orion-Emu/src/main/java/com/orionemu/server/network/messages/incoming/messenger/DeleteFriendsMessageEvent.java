package com.orionemu.server.network.messages.incoming.messenger;

import com.orionemu.server.game.players.components.types.messenger.MessengerFriend;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.messenger.MessengerDao;
import com.orionemu.server.storage.queries.player.relationships.RelationshipDao;


public class DeleteFriendsMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int friendCount = msg.readInt();

        for (int i = 0; i < friendCount; i++) {
            int userId = msg.readInt();

            MessengerFriend friend = client.getPlayer().getMessenger().getFriendById(userId);

            if (friend == null)
                continue;

            Session friendClient = friend.getSession();

            if (friendClient != null && friendClient.getPlayer() != null) {
                friendClient.getPlayer().getMessenger().removeFriend(client.getPlayer().getId());

                if (friendClient.getPlayer().getRelationships().get(client.getPlayer().getId()) != null) {
                    RelationshipDao.deleteRelationship(userId, client.getPlayer().getId());
                    friendClient.getPlayer().getRelationships().remove(client.getPlayer().getId());
                }
            } else {
                MessengerDao.deleteFriendship(userId, client.getPlayer().getId());
                RelationshipDao.deleteRelationship(userId, client.getPlayer().getId());
            }

            if (client.getPlayer().getRelationships().get(userId) != null) {
                RelationshipDao.deleteRelationship(client.getPlayer().getId(), userId);
                client.getPlayer().getRelationships().remove(userId);
            }

            client.getPlayer().getMessenger().removeFriend(userId);
        }
    }
}
