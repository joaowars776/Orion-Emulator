package com.orionemu.server.network.messages.incoming.moderation.tickets;

import com.orionemu.server.game.moderation.ModerationManager;
import com.orionemu.server.game.moderation.types.tickets.HelpTicket;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.types.RoomData;
import com.orionemu.server.logging.database.queries.LogQueries;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.moderation.tickets.ModToolTicketChatlogMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

public class ModToolTicketChatlogMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if (!client.getPlayer().getPermissions().getRank().modTool()) {
            client.disconnect();
            return;
        }

        final int ticketId = msg.readInt();
        final HelpTicket helpTicket = ModerationManager.getInstance().getTicket(ticketId);

        if (helpTicket == null || helpTicket.getModeratorId() != client.getPlayer().getId()) {
            // Doesn't exist or already picked!
            return;
        }

        final RoomData roomData = RoomManager.getInstance().getRoomData(helpTicket.getRoomId());

        if (roomData == null) return;

        client.send(new ModToolTicketChatlogMessageComposer(helpTicket, helpTicket.getRoomId(), roomData.getName(), LogQueries.getChatlogsForRoom(roomData.getId(), helpTicket.getDateSubmitted() - (30 * 60), helpTicket.getDateSubmitted() + (10 * 60))));
    }
}
