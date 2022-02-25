package com.orionemu.server.game.commands.staff.alerts;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.sessions.Session;


public class EhaCommand extends ChatCommand {

    @Override
    public void execute(Session client, String[] params) {
        if (client.getPlayer() == null || client.getPlayer().getEntity() == null) {
            return;
        }
        double timeleft = Math.round(client.getPlayer().getData().getLastUseEha() + 3600 - Orion.getTime());
if(Orion.getTime() < client.getPlayer().getData().getLastUseEha() + 3600) {
    sendNotif(Locale.getOrDefault("command.eha.cooldown", "Du kan bara använda detta kommando en gång varje timme. "+ (int) timeleft + " sekunder kvar."), client);
    return;
}

        NetworkManager.getInstance().getSessions().broadcast(
                new AdvancedAlertMessageComposer(
                        Locale.get("command.eventalert.alerttitle"),
                        Locale.get("command.eventalert.message").replace("%message%", this.merge(params)).replace("%username%", client.getPlayer().getData().getUsername()
                        ) + "<br><br><i> " + client.getPlayer().getData().getUsername() + "</i>",
                        Locale.get("command.eventalert.buttontitle"), "event:navigator/goto/" + client.getPlayer().getEntity().getRoom().getId(), "game_promo_small"));
        sendNotif(Locale.getOrDefault("command.eha.succeed", "Du har skickat en eventalert, nu måste du vänta 1 timme innan du kan skicka igen |"), client);
        client.getPlayer().getData().setLastUseEha(Orion.getTime());
    }

    @Override
    public String getPermission() {
        return "eha_command";
    }

    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.eha.description");
    }
}
