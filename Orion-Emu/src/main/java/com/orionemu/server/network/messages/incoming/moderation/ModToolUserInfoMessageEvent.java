package com.orionemu.server.network.messages.incoming.moderation;

import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.game.players.data.PlayerData;
import com.orionemu.server.game.players.types.PlayerStatistics;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.moderation.ModToolUserInfoMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.PlayerDao;


public class ModToolUserInfoMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int userId = msg.readInt();

        if (!client.getPlayer().getPermissions().getRank().modTool()) {
            client.getLogger().error(
                    ModToolUserInfoMessageEvent.class.getName() + " - tried to gather information on user: " + userId);
            client.disconnect();
            return;
        }

        PlayerData playerData = PlayerManager.getInstance().getDataByPlayerId(userId);
        PlayerStatistics playerStatistics = PlayerDao.getStatisticsById(userId);

        if (playerData == null || playerStatistics == null) {
            return;
        }

        client.send(new ModToolUserInfoMessageComposer(playerData, playerStatistics));
    }
}
