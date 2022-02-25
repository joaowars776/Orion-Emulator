package com.orionemu.server.game.landing;

import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.game.landing.types.PromoArticle;
import com.orionemu.server.game.players.data.PlayerAvatar;
import com.orionemu.server.storage.queries.landing.LandingDao;
import com.orionemu.server.tasks.OrionThreadManager;
import com.orionemu.server.utilities.Initialisable;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.TimeUnit;


public class LandingManager implements Initialisable {
    private static LandingManager landingManagerInstance;
    private static final Logger log = Logger.getLogger(LandingManager.class.getName());

    private Map<Integer, PromoArticle> articles;

    private Map<PlayerAvatar, Integer> hallOfFame;

    public LandingManager() {
    }

    @Override
    public void initialize() {
        this.loadArticles();
        this.loadHallOfFame();

        log.info("LandingManager initialized");
    }

    public static LandingManager getInstance() {
        if (landingManagerInstance == null) {
            landingManagerInstance = new LandingManager();
        }

        return landingManagerInstance;
    }

    public void loadArticles() {
        if (this.articles != null) {
            this.articles.clear();
        }

        this.articles = LandingDao.getArticles();
    }

    private void loadHallOfFame() {
        if(this.hallOfFame != null) {
            this.hallOfFame.clear();
        }

        if(OrionSettings.hallOfFameEnabled) {
            this.hallOfFame = LandingDao.getHallOfFame(OrionSettings.hallOfFameCurrency, 10);

            // Queue it to be refreshed again in X minutes
            OrionThreadManager.getInstance().executeSchedule(
                    this::loadHallOfFame,
                    OrionSettings.hallOfFameRefreshMinutes,
                    TimeUnit.MINUTES);
        }
    }

    public Map<Integer, PromoArticle> getArticles() {
        return articles;
    }

    public Map<PlayerAvatar, Integer> getHallOfFame() {
        return this.hallOfFame;
    }
}
