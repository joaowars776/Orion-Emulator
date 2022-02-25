package com.orionemu.server.game.commands.gimmicks;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.blibJackpot.BlibJackpot;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.rooms.objects.items.types.floor.wired.highscore.HighscoreClassicFloorItem;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;
import org.apache.commons.lang3.StringUtils;


public class PotCommand extends ChatCommand {
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

        int betAmound = Integer.parseInt(params[0]);

        if (betAmound > client.getPlayer().getData().getCredits()) {
            client.send(new WhisperMessageComposer(client.getPlayer().getId(), "Du har för lite mynt.", 30));
            return;
        }

        if(betAmound > 1000000) {
            client.send(new WhisperMessageComposer(client.getPlayer().getId(), "Du kan inte satsa mer än 1.000.000 mynt!", 30));
            return;
        }

        if(betAmound < 0) {
            client.send(new WhisperMessageComposer(client.getPlayer().getId(), "Du kan inte satsa minus!", 30));
            return;
        }

        if (client.getPlayer().getEntity().getRoom().getGame().getBettersUsernames().contains(client.getPlayer().getData().getUsername())) {
            client.send(new WhisperMessageComposer(client.getPlayer().getId(), "Du har redan bettat...", 30));
        } else {
            String username = client.getPlayer().getData().getUsername();
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getId(), "" + username + " satsade " + betAmound + " mynt i jackpotten!", 34));
            client.getPlayer().getData().decreaseCredits(betAmound);
            client.getPlayer().getEntity().getRoom().getGame().getBettersUsernames().add(client.getPlayer().getData().getUsername());
            client.getPlayer().getEntity().getRoom().getGame().addBet(new BlibJackpot(client.getPlayer().getData().getUsername(), betAmound));
            client.getPlayer().sendBalance();
            client.getPlayer().getData().save();
        }

        for(HighscoreClassicFloorItem item : client.getPlayer().getEntity().getRoom().getItems().getByClass(HighscoreClassicFloorItem.class)){
            item.getScoreData().getEntries().clear();

            for (BlibJackpot jackpot : client.getPlayer().getEntity().getRoom().getGame().getBetters().keySet()){
                item.addsingleEntry(jackpot.getUsername(), jackpot.getAmount());
            }

            item.sendUpdate();
            item.saveData();
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

