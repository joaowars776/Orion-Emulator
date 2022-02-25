package com.orionemu.server.game.talents;

import com.orionemu.server.game.polls.PollManager;
import com.orionemu.server.storage.queries.talents.TalentTrackDao;
import com.orionemu.server.utilities.Initialisable;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by admin on 2017-06-30.
 */
public class TalentTrackManager implements Initialisable {
    private Map<Integer, com.orionemu.server.game.talents.TalentTrackLevel> citizenshipLevels;
    private static TalentTrackManager instance;

    private static Logger log = Logger.getLogger(PollManager.class.getName());

    public TalentTrackManager(){
        citizenshipLevels = new ConcurrentHashMap<>();
    }

    @Override
    public void initialize(){
        if(citizenshipLevels != null){
            citizenshipLevels.clear();
        }

        TalentTrackDao.getLevels(citizenshipLevels);
        log.info("Loaded " + this.citizenshipLevels.size() + " talents");

        log.info("TalentManagaer initialized");
    }

    public static TalentTrackManager getInstance(){
        if(instance == null) {
            instance = new TalentTrackManager();
        }
        return instance;
    }

    public Collection<com.orionemu.server.game.talents.TalentTrackLevel> getCitizenshipLevels(){
        return citizenshipLevels.values();
    }
}
