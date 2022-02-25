package com.orionemu.server.game.commands.development;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.orionemu.server.network.sessions.Session;

public class InstanceStatsCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        StringBuilder message = new StringBuilder("<b>Orion Server - Instance Statistics </b><br><br>");

        message.append("Build: " + Orion.getBuild() + "<br><br>");
        message.append("<b>Game Statistics</b><br>Players online: " + PlayerManager.getInstance().size() + "<br>Active rooms: " + RoomManager.getInstance().getRoomInstances().size() + "<br><br>");
//        message.append("<b>Room Data</b><br>" + "Cached data instances: " + RoomManager.getInstance().getRoomDataInstances().size() + "<br>" + "<br>" + "<b>Group Data</b><br>" + "Cached data instances: " + GroupManager.getInstance().getGroupData().size() + "<br>" + "Cached instances: " + GroupManager.getInstance().getGroupInstances().size());

        client.send(new AlertMessageComposer(message.toString()));
//
//        final StringBuilder queryStats = new StringBuilder("Queries\n==============================================\n");
//
//        for(Map.Entry<String, AtomicInteger> query : SqlHelper.getQueryCounters().entrySet()) {
//            queryStats.append("\n\nQuery: " + query.getKey()).append("\nCount: " + query.getValue().get());
//        }
//
//        client.send(new MotdNotificationMessageComposer(queryStats.toString()));
    }

    @Override
    public String getPermission() {
        return "debug";
    }
    
    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}