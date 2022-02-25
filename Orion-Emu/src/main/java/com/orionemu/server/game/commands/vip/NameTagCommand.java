package com.orionemu.server.game.commands.vip;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.sessions.Session;

public class NameTagCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if(params.length > 8){
            sendNotif(Locale.getOrDefault("command.nametag.tomuch", "Max är 8 bokstäver och siffror"), client);
        }

        if(params.length == 0){
            client.getPlayer().getData().setNameTag(null);
        }

        try {
            client.getPlayer().getData().setNameTag(params[0]);
            String color = params[1];
            String hex = "000000";
            switch (color){
                case "blue":
                    hex = "1037e5";
                    break;
                case "red":
                    hex = "e20b0b";
                    break;
                case "green":
                    hex = "0ae240";
                    break;
                case "yellow":
                    hex = "e2de09";
                    break;
                case "purple":
                    hex = "580de2";
                    break;
                case "pink":
                    hex = "f704c6";
                    break;
                default:
                    hex = "000000";
                    return;
            }
            client.getPlayer().getData().setTagColor(hex);
        } catch (Exception e) {
            sendNotif(Locale.getOrDefault("command.nametag.invalid", "Använd commanden, :nametag %tag% %färg%."), client);
        }
    }

    @Override
    public String getPermission() {
        return "nametag_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.number", "%number%");
    }

    @Override
    public String getDescription() {
        return Locale.getOrDefault("command.nametag.description", "Ändra din nametag.");
    }

    @Override
    public boolean canDisable() {
        return true;
    }
}
