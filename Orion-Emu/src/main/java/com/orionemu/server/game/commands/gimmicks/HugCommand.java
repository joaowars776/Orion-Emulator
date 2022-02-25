package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.room.avatar.ActionMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;


public class HugCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendNotif(Locale.getOrDefault("command.hug.none", "Vem vill du krama?"), client);
            return;
        }

        if (client.getPlayer().getEntity().isRoomMuted() || client.getPlayer().getEntity().getRoom().getRights().hasMute(client.getPlayer().getId())) {
            sendNotif(Locale.getOrDefault("command.hug.muted", "You are muted."), client);
            return;
        }

        String kissedPlayer = params[0];
        Session kissedSession = NetworkManager.getInstance().getSessions().getByPlayerUsername(kissedPlayer);

        if (kissedSession == null) {
            sendNotif(Locale.getOrDefault("command.hug.offline", "This user is offline!"), client);
            return;
        }

        if (kissedSession.getPlayer().getEntity() == null) {
            sendNotif(Locale.getOrDefault("command.hug.notinroom", "This user is in another room."), client);
            return;
        }

        if (kissedSession.getPlayer().getData().getUsername() == client.getPlayer().getData().getUsername()) {
            sendNotif(Locale.getOrDefault("command.hug.himself", "You can't hug yourself!"), client);
            return;
        }

        int posX = kissedSession.getPlayer().getEntity().getPosition().getX();
        int posY = kissedSession.getPlayer().getEntity().getPosition().getY();
        int playerX = client.getPlayer().getEntity().getPosition().getX();
        int playerY = client.getPlayer().getEntity().getPosition().getY();

        if (!((Math.abs((posX - playerX)) >= 2) || (Math.abs(posY - playerY) >= 2))) {
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "* " + client.getPlayer().getData().getUsername() + " " + Locale.getOrDefault("command.kiss.word", "kramar") + " " + kissedSession.getPlayer().getData().getUsername() + " *", 34));
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new ActionMessageComposer(client.getPlayer().getEntity().getId(), 2));
        } else {
            client.getPlayer().getSession().send(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), Locale.getOrDefault("command.notaround", "Oops! %playername% is not near, walk to this player.").replace("%playername%", kissedSession.getPlayer().getData().getUsername()), 34));
            return;
        }
    }

    @Override
    public String getPermission() {
        return "hug_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username", "%username%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.hug.description");
    }
}