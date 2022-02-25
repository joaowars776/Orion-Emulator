package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.tasks.RoomUserWhisper;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.tasks.OrionThreadManager;
import org.apache.commons.lang3.StringUtils;


public class BetCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendNotif(Locale.getOrDefault("command.bet.none", "Hur mycket mynt vill du satsa?"), client);
            return;
        }
        boolean isNumeric = StringUtils.isNumeric(params[0]);
        if(!isNumeric){
            sendNotif("Potmängden måste vara numerisk.?", client);
            return;
        }

        int satsat = Integer.parseInt(params[0]);
        String username = client.getPlayer().getData().getUsername();
        if(Integer.parseInt(params[0]) < client.getPlayer().getData().getCredits()) {
            if(Integer.parseInt(params[0]) > 15000001) {
                client.send(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "Du kan inte satsa mer än 15.000.000 mynt!", 30));
                return;
            }
            client.getPlayer().getData().setBetAmount(Integer.parseInt(params[0]));
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "" + username + " satsar " + satsat + " mynt i spelmaskinen!", 34));
            OrionThreadManager.getInstance().executeOnceWithDelay(new RoomUserWhisper("Du har satsat " + satsat + " mynt! Dubbelklicka på spelmaskinen för att spinna!", client, client.getPlayer().getEntity()), 900);
        } else {
            client.send(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "Du har för lite mynt!", 36));
        }
    }


    @Override
    public String getPermission() {
        return "bet_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.amountx", "%amount%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.bet.description");
    }
}
