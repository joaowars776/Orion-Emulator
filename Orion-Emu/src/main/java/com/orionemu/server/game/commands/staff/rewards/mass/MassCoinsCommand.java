package com.orionemu.server.game.commands.staff.rewards.mass;

import com.orionemu.server.config.Locale;


public class MassCoinsCommand extends MassCurrencyCommand {
    @Override
    public String getPermission() {
        return "masscoins_command";
    }

    @Override
    public String getParameter() { 
        return "";
    }
    
    @Override
    public String getDescription() {
        return Locale.get("command.masscoins.description");
    }
}
