package com.orionemu.server.network.messages.outgoing.user.achievements;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.achievements.AchievementGroup;
import com.orionemu.server.game.achievements.types.Achievement;
import com.orionemu.server.game.players.components.types.achievements.AchievementProgress;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class AchievementProgressMessageComposer extends MessageComposer {

    private final AchievementGroup achievementGroup;
    private final AchievementProgress achievementProgress;

    public AchievementProgressMessageComposer(AchievementProgress achievementProgress, AchievementGroup achievementGroup) {
        this.achievementProgress = achievementProgress == null ? null : new AchievementProgress(achievementProgress.getLevel(), achievementProgress.getProgress());
        this.achievementGroup = achievementGroup;
    }

    @Override
    public short getId() {
        return Composers.AchievementProgressedMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        final Achievement achievement = this.achievementGroup.getAchievement(this.achievementProgress.getLevel());

        msg.writeInt(achievementGroup.getId());
        msg.writeInt(achievement.getLevel());
        msg.writeString(achievementGroup.getGroupName() + achievement.getLevel());
        msg.writeInt(achievement.getLevel() == 1 ? 0 :achievementGroup.getAchievement(achievement.getLevel() - 1).getProgressNeeded());
        msg.writeInt(achievement.getProgressNeeded());
        msg.writeInt(achievement.getRewardActivityPoints());
        msg.writeInt(0);
        msg.writeInt(achievementProgress.getProgress());

        if(achievementProgress.getLevel() >= achievementGroup.getLevelCount()) {
            msg.writeBoolean(true);
        } else {
            msg.writeBoolean(false);
        }

        msg.writeString(achievementGroup.getCategory().toString().toLowerCase());
        msg.writeString("");
        msg.writeInt(achievementGroup.getLevelCount());
        msg.writeInt(0);
    }
}
