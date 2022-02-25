package com.orionemu.server.network.messages.incoming.user.details;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.achievements.types.AchievementType;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.quests.types.QuestType;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.filter.FilterResult;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.avatar.UpdateInfoMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import org.apache.commons.lang3.StringUtils;


public class ChangeMottoMessageEvent implements Event {

    public void easteregg(Session client) {
        if (client.getPlayer().getData().getMotto().equals("nvm123")) {
            ChatCommand.sendNotif(Locale.getOrDefault("easteregg.text", "Koden till valvet är: 48342"), client);
        }
    }

    public void Omega(Session client) {
        if(client.getPlayer().getData().getMotto().equals("megamaze1337")) {
            ChatCommand.sendNotif(Locale.getOrDefault("easter.omega", "Code to the vault: 48342"), client);
        }
    }

    public void handle(Session client, MessageEvent msg) {
        String motto = msg.readString();

        final int timeMutedExpire = client.getPlayer().getData().getTimeMuted() - (int) Orion.getTime();

        if (client.getPlayer().getData().getTimeMuted() != 0) {
            if (client.getPlayer().getData().getTimeMuted() > (int) Orion.getTime()) {
                client.getPlayer().getSession().send(new AdvancedAlertMessageComposer(Locale.getOrDefault("command.mute.muted", "You are muted for violating the rules! Your mute will expire in %timeleft% seconds").replace("%timeleft%", timeMutedExpire + "")));
                return;
            }
        }

        if (!client.getPlayer().getPermissions().getRank().roomFilterBypass()) {
            FilterResult filterResult = RoomManager.getInstance().getFilter().filter(motto);

            if (filterResult.isBlocked()) {
                client.send(new AdvancedAlertMessageComposer(Locale.get("game.message.blocked").replace("%s", filterResult.getMessage())));
                return;
            } else if (filterResult.wasModified()) {
                motto = filterResult.getMessage();
            }
        }

        easteregg(client);
        Omega(client);
        client.getPlayer().getData().setMotto(StringUtils.abbreviate(motto, 38));
        client.getPlayer().getData().save();

        client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new UpdateInfoMessageComposer(client.getPlayer().getEntity()));
        client.send(new UpdateInfoMessageComposer(-1, client.getPlayer().getEntity()));

        client.getPlayer().getAchievements().progressAchievement(AchievementType.MOTTO, 1);
        client.getPlayer().getQuests().progressQuest(QuestType.PROFILE_CHANGE_MOTTO);
    }
}