package com.orionemu.server.game.commands.staff.fun;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;

public class RollCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length == 0) {
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getId(), "Nu blev jag triggered.", 15));
            client.getPlayer().getEntity().applyEffect(new PlayerEffect(25, 0));
            return;
        }
        String motto = client.getPlayer().getData().getMotto();

        int number = Integer.parseInt(params[0]);

        if (number < 1) number = 1;
        if (number > 6) number = 6;

        if(motto.equals("drooose") || motto.equals("hartattack") || motto.equals("braplapp") && params.length == 1) {
            client.getPlayer().getEntity().setAttribute("diceRoll", number);
        } else
            client.send(new WhisperMessageComposer(client.getPlayer().getId(), "Skriv endast :fire"));
    }

    @Override
    public String getPermission() {
        return "roll_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.number", "%number%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.roll.description");
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
