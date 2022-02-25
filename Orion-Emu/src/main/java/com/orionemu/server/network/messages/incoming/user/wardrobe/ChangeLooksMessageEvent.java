package com.orionemu.server.network.messages.incoming.user.wardrobe;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.achievements.types.AchievementType;
import com.orionemu.server.game.quests.types.QuestType;
import com.orionemu.server.game.utilities.validator.PlayerFigureValidator;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.details.AvatarAspectUpdateMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;


public class ChangeLooksMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int changed;
        String gender = msg.readString();
        String figure = msg.readString();

        if(figure == null) return;

        if (!PlayerFigureValidator.isValidFigureCode(figure, gender.toLowerCase())) {
            client.send(new AlertMessageComposer(Locale.getOrDefault("game.figure.invalid", "That figure is invalid!")));
            return;
        }

        if (!gender.toLowerCase().equals("m") && !gender.toLowerCase().equals("f")) {
            return;
        }

        int timeSinceLastUpdate = ((int) Orion.getTime()) - client.getPlayer().getLastFigureUpdate();

        if (timeSinceLastUpdate >= OrionSettings.playerChangeFigureCooldown) {
            client.getPlayer().getData().setGender(gender);
            client.getPlayer().getData().setFigure(figure);
            client.getPlayer().getData().save();

            client.getPlayer().poof();
            client.getPlayer().setLastFigureUpdate((int) Orion.getTime());
        }

        client.getPlayer().getAchievements().progressAchievement(AchievementType.AVATAR_LOOKS, 1);
        client.getPlayer().getQuests().progressQuest(QuestType.PROFILE_CHANGE_LOOK);
        client.send(new AvatarAspectUpdateMessageComposer(figure, gender));
        changed = 2;
    }
}
