package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.permissions.PermissionsManager;
import com.orionemu.server.game.permissions.types.Rank;
import com.orionemu.server.game.players.data.PlayerData;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.PlayerDao;

public class StatsCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) return;

        final String username = params[0];
        Session session = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);

        PlayerData playerData;

        if (session == null || session.getPlayer() == null || session.getPlayer().getData() == null) {
            playerData = PlayerDao.getDataByUsername(username);
        } else {
            playerData = session.getPlayer().getData();
        }

        if (playerData == null) return;

        final Rank playerRank = PermissionsManager.getInstance().getRank(playerData.getRank());

        if (playerRank.modTool() && !client.getPlayer().getPermissions().getRank().modTool()) {
            // send player info failed alert
            client.send(new AdvancedAlertMessageComposer(Locale.getOrDefault("command.playerinfo.title", "Spelare Information") + ": " + username, Locale.getOrDefault("command.playerinfo.staff", "You cannot view the information of a staff member!")));
            return;
        }

        final StringBuilder userInfo = new StringBuilder();

        userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.username", "Användarnamn") + "</b>: " + playerData.getUsername() + "<br>");
        userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.motto", "Motto") + "</b>: " + playerData.getMotto() + "<br>");
        userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.gender", "Kön") + "</b>: " + (playerData.getGender().toLowerCase().equals("m") ? Locale.getOrDefault("command.playerinfo.male", "Male") : Locale.getOrDefault("command.playerinfo.female", "Female")) + "<br>");
        userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.status", "Status") + "</b>: " + (session == null ? Locale.getOrDefault("command.playerinfo.offline", "Offline") : Locale.getOrDefault("command.playerinfo.online", "Online")) + "<br>");
        userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.achievementPoints", "Activitypoints") + "</b>: " + playerData.getAchievementPoints() + "<br><br>");

        if (client.getPlayer().getPermissions().getRank().modTool()) {
            userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.rank", "Rank") + "</b>: " + playerData.getRank() + "<br><br>");
        }

        userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.currencyBalances", "Plånbok") + "</b><br>");
        userInfo.append("<i>" + playerData.getCredits() + " " + Locale.getOrDefault("command.playerinfo.credits", "Mynt") + "</i><br>");
        userInfo.append("<i>" + playerData.getVipPoints() + " " + Locale.getOrDefault("command.playerinfo.diamonds", "Diamanter") + "</i><br>");

        userInfo.append("<i>" + playerData.getActivityPoints() + " " + Locale.getOrDefault("command.playerinfo.activityPoints", "Duckets") + "</i><br><br>");

        client.send(new AdvancedAlertMessageComposer(Locale.getOrDefault("command.playerinfo.title", "Stats om spelare") + ": " + username, userInfo.toString()));
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public String getPermission() {
        return "stats_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username", "%username%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.stats.description");
    }
}
