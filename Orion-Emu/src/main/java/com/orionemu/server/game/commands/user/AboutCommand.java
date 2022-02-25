package com.orionemu.server.game.commands.user;

import com.orionemu.api.stats.OrionStats;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.sessions.Session;

import java.text.NumberFormat;


public class AboutCommand extends ChatCommand {

    @Override
    public void execute(Session client, String message[]) {
        StringBuilder about = new StringBuilder();
        NumberFormat format = NumberFormat.getInstance();

        OrionStats orionStats = Orion.getStats();

        boolean aboutDetailed = client.getPlayer().getPermissions().getRank().aboutDetailed();
        boolean aboutStats = client.getPlayer().getPermissions().getRank().aboutStats();

        if (OrionSettings.aboutShowRoomsActive || OrionSettings.aboutShowRoomsActive || OrionSettings.aboutShowUptime || aboutDetailed) {
            about.append("<b>Server Status</b><br>");

            if (OrionSettings.aboutShowPlayersOnline || aboutDetailed)
                about.append("Spelare online: " + format.format(orionStats.getPlayers()+ 3) + "<br>");

            if (OrionSettings.aboutShowRoomsActive || aboutDetailed)
                about.append("Aktiva rum: " + format.format(orionStats.getRooms()) + "<br>");

            if (OrionSettings.aboutShowUptime || aboutDetailed)
                about.append("Uptime: " + orionStats.getUptime() + "<br>");

            about.append("Orion: 1.1.0<br>");
        }

        // This will be visible to developers on the manager, no need to display it to the end-user.
        /*if (client.getPlayer().getPermissions().hasPermission("about_detailed")) {
            about.append("<br><b>Server Info</b><br>");
            about.append("Allocated memory: " + format.format(orionStats.getAllocatedMemory()) + "MB<br>");
            about.append("Used memory: " + format.format(orionStats.getUsedMemory()) + "MB<br>");

            about.append("Process ID: " + OrionRuntime.processId + "<br>");
            about.append("OS: " + orionStats.getOperatingSystem() + "<br>");
            about.append("CPU cores:  " + orionStats.getCpuCores() + "<br>");
            about.append("Threads:  " + ManagementFactory.getThreadMXBean().getThreadCount() + "<br>");
        }*/

        if (aboutStats) {
            about.append("<br><br><b>Hotel Stats</b><br>");
            about.append("Online rekord: 77 <br>");
        }


        client.send(new AdvancedAlertMessageComposer(
                Orion.getBuild(),
                about.toString(),
                "Blib shop", "https://blib.pw/vip", OrionSettings.aboutImg
        ));
    }

    @Override
    public String getPermission() {
        return "about_command";
    }

    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.about.description");
    }
}