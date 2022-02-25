package com.orionemu.server.network.messages.incoming.moderation;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.moderation.BanManager;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

public class ModerationMuteUserMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final int playerId = msg.readInt();
        final int muteLength = msg.readInt();

        // TODO: use length.

        if(!client.getPlayer().getPermissions().getRank().modTool()) {
            client.disconnect();

            return;
        }

        Session session = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

        if (session != null) {
            session.send(new AdvancedAlertMessageComposer(Locale.get("command.mute.muted")));
        }

        BanManager.getInstance().mute(playerId);
    }
}
