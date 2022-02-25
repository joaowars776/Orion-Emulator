package com.orionemu.server.network.messages.incoming.room.action;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.filter.FilterResult;
import com.orionemu.server.game.rooms.objects.entities.RoomEntity;
import com.orionemu.server.game.rooms.objects.entities.RoomEntityType;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.logging.LogManager;
import com.orionemu.server.logging.entries.RoomChatLogEntry;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.avatar.MutedMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

import java.util.Timer;


public class WhisperMessageEvent implements Event {
    private Timer timer = new Timer();

    public void handle(Session client, MessageEvent msg) {
        String text = msg.readString();

        String user = text.split(" ")[0];
        String message = text.substring(user.length() + 1);
		
        final int timeMutedExpire = client.getPlayer().getData().getTimeMuted() - (int) Orion.getTime();

        if (client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) {
            return;
        }

        if(!client.getPlayer().getEntity().isVisible()) {
            return;
        }

        if (client.getPlayer().getData().getTimeMuted() != 0) {
            if (client.getPlayer().getData().getTimeMuted() > (int) Orion.getTime()) {
                client.getPlayer().getSession().send(new MutedMessageComposer(timeMutedExpire));
                return;
            }
        }

        final Room room = client.getPlayer().getEntity().getRoom();

        RoomEntity userTo = room.getEntities().getEntityByName(user, RoomEntityType.PLAYER);

        if (userTo == null || user.equals(client.getPlayer().getData().getUsername()))
            return;
            
        if (!((PlayerEntity) userTo).getPlayer().getEntity().isVisible())
            return;

        if (client.getPlayer().getChatMessageColour() != null) {
            message = "@" + client.getPlayer().getChatMessageColour() + "@" + message;

            if (message.toLowerCase().startsWith("@" + client.getPlayer().getChatMessageColour() + "@:")) {
                message = message.toLowerCase().replace("@" + client.getPlayer().getChatMessageColour() + "@:", ":");
            }
        }

        String filteredMessage = TalkMessageEvent.filterMessage(message);

        if (filteredMessage == null) {
            return;
        }

        if (!client.getPlayer().getPermissions().getRank().roomFilterBypass()) {
            FilterResult filterResult = RoomManager.getInstance().getFilter().filter(message);

            if (filterResult.isBlocked()) {
                client.send(new AdvancedAlertMessageComposer(Locale.get("game.message.blocked").replace("%s", filterResult.getMessage())));
                client.getLogger().info("Filter detected a blacklisted word in message: \"" + message + "\"");
                return;
            } else if (filterResult.wasModified()) {
                filteredMessage = filterResult.getMessage();
            }
        }


        if (client.getPlayer().getEntity().onChat(filteredMessage)) {
            try {
                if (LogManager.ENABLED)
                    LogManager.getInstance().getStore().getLogEntryContainer().put(new RoomChatLogEntry(room.getId(), client.getPlayer().getId(), Locale.getOrDefault("game.logging.whisper", "<Whisper to %username%>").replace("%username%", user) + " " + message));
            } catch (Exception ignored) {

            }

            if (!((PlayerEntity) userTo).getPlayer().ignores(client.getPlayer().getId()))
                ((PlayerEntity) userTo).getPlayer().getSession().send(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), filteredMessage));

            for (PlayerEntity entity : client.getPlayer().getEntity().getRoom().getEntities().getWhisperSeers()) {
                if (entity.getPlayer().getId() != client.getPlayer().getId() && !user.equals(entity.getUsername()))
                    entity.getPlayer().getSession().send(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "Whisper to " + user + ": " + filteredMessage));
            }
        }

        client.send(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), filteredMessage));
    }
}