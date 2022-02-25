package com.orionemu.server.network.messages.incoming.moderation;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.moderation.BanManager;
import com.orionemu.server.game.moderation.types.BanType;
import com.orionemu.server.game.permissions.PermissionsManager;
import com.orionemu.server.game.permissions.types.Rank;
import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.game.players.data.PlayerData;
import com.orionemu.server.game.players.types.PlayerStatistics;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.storage.queries.player.PlayerDao;


public class ModToolBanUserMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        final int userId = msg.readInt();
        final String message = msg.readString();
        final int length = msg.readInt();

        if (!client.getPlayer().getPermissions().getRank().modTool()) {
            client.disconnect();
            return;
        }

        boolean ipBan = msg.readBoolean();
        boolean machineBan = msg.readBoolean();

        long expire = Orion.getTime() + (length * 3600);

        Session user = NetworkManager.getInstance().getSessions().getByPlayerId(userId);

        if (user == null) {
            PlayerData playerData = PlayerManager.getInstance().getDataByPlayerId(userId);

            if (playerData == null) {
                return;
            }

            final Rank playerRank = PermissionsManager.getInstance().getRank(playerData.getRank());

            if (!playerRank.bannable()) {
                client.send(new AlertMessageComposer(Locale.getOrDefault("mod.ban.nopermission", "You do not have permission to ban this player!")));
                return;
            }


            this.banPlayer(ipBan, machineBan, expire, userId, client.getPlayer().getId(), length, message, playerData.getIpAddress(), "");
            return;
        }

        if (user == client) {
            return;
        }

        if (!user.getPlayer().getPermissions().getRank().bannable()) {
            client.send(new AlertMessageComposer(Locale.getOrDefault("mod.ban.nopermission", "You do not have permission to ban this player!")));
            return;
        }

        user.disconnect("banned");

        this.banPlayer(ipBan, machineBan, expire, user.getPlayer().getId(), client.getPlayer().getId(), length, message, user.getIpAddress(), machineBan ? user.getUniqueId() : "");
    }

    private void banPlayer(boolean ipBan, boolean machineBan, long expire, int playerId, int moderatorId, int length, String message, String ipAddress, String uniqueId) {
        this.updateStats(playerId);

        if (ipBan) {
            BanManager.getInstance().banPlayer(BanType.IP, ipAddress, length, expire, message, moderatorId);
        }

        if (machineBan && !uniqueId.isEmpty()) {
            BanManager.getInstance().banPlayer(BanType.MACHINE, uniqueId, length, expire, message, moderatorId);
        }

        BanManager.getInstance().banPlayer(BanType.USER, playerId + "", length, expire, message, moderatorId);
    }

    private void updateStats(int playerId) {
        PlayerStatistics playerStatistics = PlayerDao.getStatisticsById(playerId);

        if (playerStatistics != null) {
            playerStatistics.incrementBans(1);
        }
    }
}
