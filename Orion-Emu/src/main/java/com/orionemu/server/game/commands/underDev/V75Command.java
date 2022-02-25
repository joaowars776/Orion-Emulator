package com.orionemu.server.game.commands.underDev;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.blibV75.BlibV75;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by SpreedBlood on 2017-08-13.
 */
public class V75Command extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length == 1) {
            sendNotif(":v75 %hästNamn% %mängd%", client);
            return;
        }

        if (client.getPlayer().getEntity().getRoom().getGame().getV75Usernames().contains(client.getPlayer().getData().getUsername())) {
            sendNotif("Du har redan bettat!", client);
            return;
        }

        if(client.getPlayer().getEntity().getRoom().getGame().getV75Instance() != null){
            if(client.getPlayer().getEntity().getRoom().getGame().getV75Instance().getHasStarted()){
                sendNotif("Var vänlig vänta tills denna omgång är klar!", client);
                return;
            }
        }

        try {
            Collection<String> petNames = new LinkedList<>();
            petNames.add("lisa");
            petNames.add("sten");
            petNames.add("zlatan");
            petNames.add("kalle");

            boolean isNumeric = StringUtils.isNumeric(params[1]);
            if (!isNumeric) {
                sendNotif("Betmängden måste vara nummerisk, t.ex: :v75 Kalle 1000.", client);
                return;
            }
            String horseName = params[0].toLowerCase();
            int betAmound = Integer.parseInt(params[1]);

            if(client.getPlayer().getData().getCredits() < betAmound) {
                sendNotif("Du kan inte betta mer mynt än vad du har!", client);
                return;
            }
            if (!petNames.contains(horseName)) {
                sendNotif("Var vänlig välj bland hästarna.", client);
                return;
            }
            client.getPlayer().getEntity().getRoom().getGame().getV75StringMap().put(new BlibV75(client.getPlayer().getData().getUsername(), betAmound), horseName);
            client.getPlayer().getEntity().getRoom().getGame().getV75Usernames().add(client.getPlayer().getData().getUsername());
            petNames.clear();
            client.getPlayer().getData().decreaseCredits(betAmound);
            client.getPlayer().sendBalance();
            client.getPlayer().getData().save();
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getId(), "" + client.getPlayer().getData().getUsername() + " bettade " + betAmound + " på " + horseName + "!", 30));
        } catch (ArrayIndexOutOfBoundsException ex) {
        }
    }


    @Override
    public String getPermission() {
        return "pot_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.amountx", "%amount%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.pot.description");
    }
}

