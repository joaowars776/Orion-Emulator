package com.orionemu.server.network.messages.incoming.user.profile;

import com.orionemu.server.game.players.components.RelationshipComponent;
import com.orionemu.server.game.players.components.types.messenger.MessengerFriend;
import com.orionemu.server.game.players.components.types.messenger.RelationshipLevel;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.messenger.UpdateFriendStateMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.relationships.RelationshipDao;


public class SetRelationshipMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int user = msg.readInt();
        int level = msg.readInt();

        if(client.getPlayer().getMessenger().getFriendById(user) == null) {
            return;
        }

        final MessengerFriend friend = client.getPlayer().getMessenger().getFriendById(user);

        RelationshipComponent relationships = client.getPlayer().getRelationships();

        if(relationships.getRelationships().size() >= 100) {
            // TODO: Allow this to be configured.
            return;
        }

        if (level == 0) {
            RelationshipDao.deleteRelationship(client.getPlayer().getId(), user);

            if (relationships.getRelationships().containsKey(user)) {
                relationships.getRelationships().remove(user);
            }
        } else {
            final String levelString = level == 1 ? "HEART" : level == 2 ? "SMILE" : "BOBBA";
            final RelationshipLevel relationshipLevel = RelationshipLevel.valueOf(levelString);

            if (relationships.getRelationships().containsKey(user)) {

                RelationshipDao.updateRelationship(client.getPlayer().getId(), user, levelString);
                relationships.getRelationships().replace(user, RelationshipLevel.valueOf(levelString));
            } else {

                RelationshipDao.createRelationship(client.getPlayer().getId(), user, levelString);
                relationships.getRelationships().put(user, relationshipLevel);
            }
        }

        client.send(new UpdateFriendStateMessageComposer(friend.getAvatar(),
                friend.isOnline(), friend.isInRoom(), client.getPlayer().getRelationships().get(user)));
    }
}
