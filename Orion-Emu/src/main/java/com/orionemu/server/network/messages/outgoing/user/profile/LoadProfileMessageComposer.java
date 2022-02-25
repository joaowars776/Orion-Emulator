package com.orionemu.server.network.messages.outgoing.user.profile;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.groups.types.GroupData;
import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.game.players.data.PlayerData;
import com.orionemu.server.game.players.types.PlayerStatistics;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.outgoing.user.details.UserObjectMessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.ArrayList;
import java.util.List;


public class LoadProfileMessageComposer extends MessageComposer {
    private final PlayerData player;
    private final PlayerStatistics stats;
    private final List<Integer> groups;
    private final boolean isMyFriend;
    private final boolean requestSent;

    public LoadProfileMessageComposer(PlayerData player, PlayerStatistics stats, List<Integer> groups, boolean isMyFriend, boolean hasSentRequest) {
        this.player = player;
        this.stats = stats;
        this.groups = groups;
        this.isMyFriend = isMyFriend;
        this.requestSent = hasSentRequest;
    }

    @Override
    public short getId() {
        return Composers.ProfileInformationMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(player.getId());
        msg.writeString(player.getUsername());
        msg.writeString(player.getFigure());
        msg.writeString(player.getMotto());

        boolean isTimestamp = false;
        int timestamp = 0;

        try {
            timestamp = Integer.parseInt(player.getRegDate());
            isTimestamp = true;
        } catch (Exception ignored) {
        }

        msg.writeString(isTimestamp ? UserObjectMessageComposer.getDate(timestamp) : player.getRegDate());
        msg.writeInt(player.getAchievementPoints());
        msg.writeInt(stats.getFriendCount());
        msg.writeBoolean(isMyFriend);
        msg.writeBoolean(requestSent);
        msg.writeBoolean(PlayerManager.getInstance().isOnline(player.getId()));
        msg.writeInt(0); // groups

        msg.writeInt((int) Orion.getTime() - player.getLastVisit());
        msg.writeBoolean(true);
    }
}
