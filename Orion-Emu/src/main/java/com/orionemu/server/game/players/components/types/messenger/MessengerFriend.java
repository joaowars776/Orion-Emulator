package com.orionemu.server.game.players.components.types.messenger;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.game.players.data.PlayerAvatar;
import com.orionemu.server.game.players.data.PlayerAvatarData;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.sessions.Session;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MessengerFriend {
    private int userId;
    private PlayerAvatar playerAvatar;
    private Session client;

    public MessengerFriend(ResultSet data) throws SQLException {
        this.userId = data.getInt("user_two_id");
        this.playerAvatar = new PlayerAvatarData(this.userId, data.getString("username"), data.getString("figure"), data.getString("motto"));
    }

    public MessengerFriend(int userId) {
        this.userId = userId;
    }

    public boolean isInRoom() {
        if (!isOnline()) {
            return false;
        }

        Session client = NetworkManager.getInstance().getSessions().getByPlayerId(this.userId);

        // Could have these in 1 statement, but to make it easier to read - lets just leave it like this. :P
        if (client == null || client.getPlayer() == null || client.getPlayer().getEntity() == null) {
            return false;
        }

        if (!client.getPlayer().getEntity().isVisible())
            return false;

        return true;
    }

    public Session updateClient() {
        this.client = NetworkManager.getInstance().getSessions().getByPlayerId(userId);

        return this.client;
    }

    public PlayerAvatar getAvatar() {
        if (this.getSession() != null && this.getSession().getPlayer() != null) {
            return this.getSession().getPlayer().getData();
        }

        return this.playerAvatar;
    }

    public void serialize(IComposer msg) {
        msg.writeInt(userId);
        msg.writeString(playerAvatar.getUsername());
        msg.writeInt(1);
        msg.writeBoolean(this.client != null);
        msg.writeBoolean(isInRoom());
        msg.writeString(playerAvatar.getFigure());
        msg.writeInt(0);
        msg.writeString(playerAvatar.getMotto());
        msg.writeString("");
        msg.writeString("");
        msg.writeBoolean(true);
        msg.writeBoolean(true);
        msg.writeBoolean(false);
        msg.writeBoolean(false);
        msg.writeBoolean(false);
    }

    public int getUserId() {
        return this.userId;
    }

    public boolean isOnline() {
        return PlayerManager.getInstance().isOnline(userId);
    }

    public Session getSession() {
        return NetworkManager.getInstance().getSessions().getByPlayerId(this.userId);
    }
}
