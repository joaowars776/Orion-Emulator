package com.orionemu.server.game.talents;

import com.orionemu.server.storage.queries.talents.TalentTrackDao;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TalentTrackLevel {

    private String type;
    private int level;

    public List<String> getDataActions() {
        return dataActions;
    }

    private List<String> dataActions;

    public List<String> getDataGifts() {
        return dataGifts;
    }

    private List<String> dataGifts;
    private Map<Integer, TalentTrackSubLevel> sublevels;

    public TalentTrackLevel(String type, int level, String dataActions, String dataGifts) {
        this.type = type;
        this.level = level;

        for (String str : dataActions.split("|", -1)) {
            if (this.dataActions == null) {
                this.dataActions = new LinkedList<>();
            }
            this.dataActions.add(str);
        }

        for (String str : dataGifts.split("|", -1)) {
            if (this.dataGifts == null) {
                this.dataGifts = new LinkedList<>();
            }
            this.dataGifts.add(str);
        }

        this.sublevels = new ConcurrentHashMap<>();
        TalentTrackDao.getSubLevels(this.sublevels, this.level);
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public Collection<TalentTrackSubLevel> getSubLevels() {
        return sublevels.values();
    }
}
