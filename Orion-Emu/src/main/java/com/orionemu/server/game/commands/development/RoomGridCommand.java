package com.orionemu.server.game.commands.development;

import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.sessions.Session;

public class RoomGridCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        client.send(new AdvancedAlertMessageComposer("Entity Grid", client.getPlayer().getEntity().getRoom().getMapping().visualiseEntityGrid()));
    }

    @Override
    public String getPermission() {
        return "debug";
    }
    
    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
