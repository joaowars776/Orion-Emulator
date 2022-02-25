package com.orionemu.server.network.messages.outgoing.messenger;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.game.players.components.types.messenger.RelationshipLevel;
import com.orionemu.server.game.players.data.PlayerAvatar;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class UpdateFriendStateMessageComposer extends MessageComposer {
    private final PlayerAvatar playerAvatar;
    private final Group group;

    private final boolean online;
    private final boolean inRoom;

    private int action;
    private int friendId;

    private final RelationshipLevel relationshipLevel;

    public UpdateFriendStateMessageComposer(final PlayerAvatar playerAvatar, final boolean online, final boolean inRoom, final RelationshipLevel level) {
        this.playerAvatar = playerAvatar;
        this.group = null;
        this.online = online;
        this.inRoom = inRoom;
        this.relationshipLevel = level;
    }

    public UpdateFriendStateMessageComposer(final Group group) {
        this.playerAvatar = null;
        this.group = group;
        this.online = true;
        this.inRoom = false;
        this.relationshipLevel = null;
    }

    public UpdateFriendStateMessageComposer(int action, int friendId) {
        this.playerAvatar = null;
        this.group = null;
        this.online = false;
        this.inRoom = false;
        this.action = action;
        this.friendId = friendId;
        this.relationshipLevel = null;
    }

    @Override
    public short getId() {
        return Composers.FriendListUpdateMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        if (this.playerAvatar == null && this.group == null) {
            msg.writeInt(0);
            msg.writeInt(1);
            msg.writeInt(this.action);
            msg.writeInt(this.friendId);

            return;
        }

        msg.writeInt(0);
        msg.writeInt(1);
        msg.writeInt(0);
        msg.writeInt(playerAvatar.getId());
        msg.writeString(playerAvatar.getUsername());
        msg.writeInt(1);
        msg.writeBoolean(online);
        msg.writeBoolean(inRoom);
        msg.writeString(playerAvatar.getFigure());
        msg.writeInt(0);
        msg.writeString(playerAvatar.getMotto());
        msg.writeString(""); // facebook name ?
        msg.writeString("");
        msg.writeBoolean(true);
        msg.writeBoolean(true);
        msg.writeBoolean(false);
        msg.writeBoolean(false);
        msg.writeBoolean(false);
    }
}
