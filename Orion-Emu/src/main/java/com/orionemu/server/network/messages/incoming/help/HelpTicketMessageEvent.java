package com.orionemu.server.network.messages.incoming.help;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.moderation.ModerationManager;
import com.orionemu.server.game.rooms.types.components.types.ChatMessage;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.help.TicketSentMessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.google.common.collect.Lists;

import java.util.List;


public class HelpTicketMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        boolean hasActiveTicket = ModerationManager.getInstance().getActiveTicketByPlayerId(client.getPlayer().getId()) != null;

        if (hasActiveTicket) {
            client.send(new AlertMessageComposer(Locale.get("help.ticket.pending.title"), Locale.get("help.ticket.pending.message")));
            return;
        }

        String message = msg.readString();
        int category = msg.readInt();
        int reportedId = msg.readInt();
        int timestamp = (int) Orion.getTime();
        int roomId = client.getPlayer().getEntity() != null ? client.getPlayer().getEntity().getRoom().getId() : 0;

        int junk = msg.readInt();
        int chatCount = msg.readInt();

        final List<ChatMessage> chatMessages = Lists.newArrayList();

        for (int i = 0; i < chatCount; i++) {
            final int playerId = msg.readInt();

            if (reportedId == 0) {
                reportedId = playerId;
            }

            final String chatMessage = msg.readString();

            chatMessages.add(new ChatMessage(playerId, chatMessage));
        }

        ModerationManager.getInstance().createTicket(client.getPlayer().getId(), message, category, reportedId, timestamp, roomId, chatMessages);
        client.send(new TicketSentMessageComposer());
    }
}