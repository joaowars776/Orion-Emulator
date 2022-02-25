package com.orionemu.server.network.messages.incoming.user.profile;

import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.game.players.data.PlayerData;
import com.orionemu.server.game.players.types.PlayerStatistics;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.user.profile.LoadProfileMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.groups.GroupDao;
import com.orionemu.server.storage.queries.player.PlayerDao;

import java.util.List;


public class GetProfileMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int userId = msg.readInt();

        PlayerData data = userId == client.getPlayer().getId() ? client.getPlayer().getData() : null;
        PlayerStatistics stats = data != null ? client.getPlayer().getStats() : null;
        List<Integer> groups = data != null ? client.getPlayer().getGroups() : null;

        if (data == null) {
            if (NetworkManager.getInstance().getSessions().getByPlayerId(userId) != null) {
                data = NetworkManager.getInstance().getSessions().getByPlayerId(userId).getPlayer().getData();
                stats = NetworkManager.getInstance().getSessions().getByPlayerId(userId).getPlayer().getStats();
                groups = NetworkManager.getInstance().getSessions().getByPlayerId(userId).getPlayer().getGroups();
            }
        }

        if (data == null) {
            data = PlayerManager.getInstance().getDataByPlayerId(userId);
            stats = PlayerDao.getStatisticsById(userId);
            groups = GroupDao.getIdsByPlayerId(userId);
        }

        if (data == null) {
            return;
        }

        client.send(new LoadProfileMessageComposer(data, stats, groups, client.getPlayer().getMessenger().getFriendById(userId) != null, false));
    }
}
