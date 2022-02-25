package com.orionemu.server.game;

import com.orionemu.api.networking.sessions.BaseSession;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.game.achievements.types.AchievementType;
import com.orionemu.server.game.moderation.BanManager;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.user.details.UserObjectMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.PlayerDao;
import com.orionemu.server.storage.queries.system.StatisticsDao;
import com.orionemu.server.tasks.OrionTask;
import com.orionemu.server.tasks.OrionThreadManager;
import com.orionemu.server.utilities.Initialisable;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class GameCycle implements OrionTask, Initialisable {
    private static final int interval = 1;
    private static final int PLAYER_REWARD_INTERVAL = 15; // minutes

    private static GameCycle gameThreadInstance;

    private static Logger log = Logger.getLogger(GameCycle.class.getName());

    private ScheduledFuture gameFuture;

    private boolean active = false;

    private int currentOnlineRecord = 0;
    private int onlineRecord = 0;

    public GameCycle() {

    }

    @Override
    public void initialize() {
        this.gameFuture = OrionThreadManager.getInstance().executePeriodic(this, interval, interval, TimeUnit.MINUTES);
        this.active = true;

        this.onlineRecord = StatisticsDao.getPlayerRecord();
    }

    public static GameCycle getInstance() {
        if (gameThreadInstance == null)
            gameThreadInstance = new GameCycle();

        return gameThreadInstance;
    }

    @Override
    public void run() {
        try {
            if (!this.active) {
                return;
            }

            BanManager.getInstance().processBans();

            final int usersOnline = NetworkManager.getInstance().getSessions().getUsersOnlineCount();
            boolean updateOnlineRecord = false;

            if (usersOnline > this.currentOnlineRecord) {
                this.currentOnlineRecord = usersOnline;
            }

            if (usersOnline > this.onlineRecord) {
                this.onlineRecord = usersOnline;
                updateOnlineRecord = true;
            }

            this.processSession();

            if (!updateOnlineRecord)
                StatisticsDao.saveStatistics(usersOnline, RoomManager.getInstance().getRoomInstances().size(), Orion.getBuild());
            else
                StatisticsDao.saveStatistics(usersOnline, RoomManager.getInstance().getRoomInstances().size(), Orion.getBuild(), this.onlineRecord);


        } catch (Exception e) {
            log.error("Error during game thread", e);
        }
    }

    private void processSession() throws Exception {

        final Calendar calendar = Calendar.getInstance();

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        final boolean updateDaily = hour == 0 && minute == 0;
        final int dailyRespects = 3;
        final int dailyScratches = 3;

        if (OrionSettings.onlineRewardEnabled || updateDaily) {
            for (BaseSession client : NetworkManager.getInstance().getSessions().getSessions().values()) {
                try {
                    if (!(client instanceof Session) || client.getPlayer() == null || client.getPlayer().getData() == null) {
                        continue;
                    }

                    if((Orion.getTime() - ((Session) client).getLastPing()) >= 300) {
                        client.disconnect();
                        continue;
                    }

                    if (updateDaily) {
                        //  TODO: put this in config.
                        client.getPlayer().getStats().setDailyRespects(dailyRespects);
                        client.getPlayer().getStats().setScratches(dailyScratches);

                        client.send(new UserObjectMessageComposer(((Session) client).getPlayer()));
                    }

                    ((Session) client).getPlayer().getAchievements().progressAchievement(AchievementType.ONLINE_TIME, 1);

                    final boolean needsReward = (Orion.getTime() - client.getPlayer().getLastReward()) >= (60 * PLAYER_REWARD_INTERVAL);

                    if (needsReward) {
                        if (OrionSettings.onlineRewardCredits > 0) {
                            client.getPlayer().getData().increaseCredits(OrionSettings.onlineRewardCredits);
                        }

                        if (OrionSettings.onlineRewardDuckets > 0) {
                            client.getPlayer().getData().increaseActivityPoints(OrionSettings.onlineRewardDuckets);
                        }

                        client.getPlayer().sendBalance();
                        client.getPlayer().getData().save();

                        client.getPlayer().setLastReward(Orion.getTime());
                    }
                } catch (Exception e) {
                    log.error("Error while cycling rewards", e);
                }
            }

            if(updateDaily) {
                PlayerDao.dailyPlayerUpdate(dailyRespects, dailyScratches);
            }
        }
    }

    public void stop() {
        this.active = false;
        this.gameFuture.cancel(false);
    }

    public int getCurrentOnlineRecord() {
        return this.currentOnlineRecord;
    }

    public int getOnlineRecord() {
        return this.onlineRecord;
    }
}
