package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.avatar.UpdateInfoMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;


public class TillsammansCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 1) {
            sendNotif(Locale.getOrDefault("command.tsm.none", "Vem vill du bli tillsammans med?"), client);
            return;
        }

        if (client.getPlayer().getEntity().isRoomMuted() || client.getPlayer().getEntity().getRoom().getRights().hasMute(client.getPlayer().getId())) {
            sendNotif(Locale.getOrDefault("command.user.muted", "You are muted."), client);
            return;
        }

        String username = client.getPlayer().getData().getUsername();
        Session user = NetworkManager.getInstance().getSessions().getByPlayerUsername(params[0]);
        String targetUsername = user.getPlayer().getData().getUsername();
        boolean ask = client.getPlayer().getData().getAsk();

        if (targetUsername == client.getPlayer().getData().getUsername()) {
            sendNotif(Locale.getOrDefault("command.kiss.himself", "Du kan inte bli tillsammans med dig själv!"), client);
            return;
        }

        if(user.getPlayer().getEntity() != null && params.length == 1) {
            client.send(new WhisperMessageComposer(client.getPlayer().getId(), "Du frågat " + targetUsername + " om ni ska bli tillsammans, och väntar nu svar...", 21));
            user.send(new AlertMessageComposer("" + username + " vill bli tillsammans med dig.\r\rSkriv:\r<b>:tillsammans " + username + " ja \r</b>Om du vill bli tillsammans med " + username + "\r<b>:tillsammans " + username + " nej</b>\rOm du inte vill bli tillsammans med " + username + ""));

            client.getPlayer().getData().setAsk(true);
            user.getPlayer().getData().setAsk(true);

        } else if(user.getPlayer().getEntity() == null) {
            sendNotif(Locale.getOrDefault("command.tsm.none", "Spelaren är inte i ditt rum"), client);
        }

        if(ask && user.getPlayer().getData().getAsk() && params[1].equals("ja")) {

            client.getPlayer().getData().setMotto("Tillsammans med: " + targetUsername + "");
            user.getPlayer().getData().setMotto("Tillsammans med: " + username + "");

            client.send(new WhisperMessageComposer(client.getPlayer().getId(), "Du är nu tillsammans med " + targetUsername + "", 21));
            user.send(new WhisperMessageComposer(client.getPlayer().getId(), "Du är nu tillsammans med " + username + "", 21));

            client.getPlayer().getData().save();
            user.getPlayer().getData().save();

            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new UpdateInfoMessageComposer(client.getPlayer().getEntity()));
            client.send(new UpdateInfoMessageComposer(-1, client.getPlayer().getEntity()));

            user.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new UpdateInfoMessageComposer(user.getPlayer().getEntity()));
            user.send(new UpdateInfoMessageComposer(-1, user.getPlayer().getEntity()));
            user.send(new UpdateInfoMessageComposer(-1, user.getPlayer().getEntity()));

            client.getPlayer().getData().setAsk(false);
            user.getPlayer().getData().setAsk(true);
        } else if(ask && params[1].equals("nej")) {

            user.send(new WhisperMessageComposer(client.getPlayer().getId(), ""+username+" svarade tyvärr nej.", 21));
            client.send(new WhisperMessageComposer(client.getPlayer().getId(), "Du svarade nej.", 21));

            client.getPlayer().getData().setAsk(false);
            user.getPlayer().getData().setAsk(false);
        }
    }

    @Override
    public String getPermission() {
        return "tillsammans_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username", "%username%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.tillsammans.description");
    }
}
