package com.orionemu.server.game.talents;

/**
 * Created by admin on 2017-06-30.
 */
public class TalentTrackSubLevel {
    public int level;

    public String getBadge() {
        return badge;
    }

    public String badge;

    public int getRequiredProgress() {
        return requiredProgress;
    }

    public int requiredProgress;

    public TalentTrackSubLevel(int level, String badge, int requiredProgress){
        this.level = level;
        this.badge = badge;
        this.requiredProgress = requiredProgress;
    }
}
