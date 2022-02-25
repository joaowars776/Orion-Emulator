package com.orionemu.server.storage.queue.types;

import com.orionemu.server.game.players.data.PlayerData;
import com.orionemu.server.storage.queries.player.PlayerDao;
import com.orionemu.server.storage.queue.StorageQueue;
import com.orionemu.server.tasks.OrionTask;
import com.orionemu.server.tasks.OrionThreadManager;
import com.orionemu.server.utilities.Initialisable;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PlayerDataStorageQueue implements Initialisable, OrionTask, StorageQueue<PlayerData> {
    private static final Logger log = Logger.getLogger(PlayerDataStorageQueue.class.getName());
    private static PlayerDataStorageQueue instance;

    private ScheduledFuture future;

    private Map<Integer, PlayerData> playerData;

    public PlayerDataStorageQueue() {
        this.playerData = new ConcurrentHashMap<>();
    }

    @Override
    public void initialize() {
        this.future = OrionThreadManager.getInstance().executePeriodic(this, 0, 1500, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        if(playerData.size() == 0) {
            // No data to be saved!
            return;
        }

        log.debug("Saving " + this.playerData.size() + " player data instances");

        PlayerDao.saveBatch(this.playerData);

        this.playerData.clear();
    }

    @Override
    public void queueSave(final PlayerData playerData) {
        if(this.playerData.get(playerData.getId()) != null) {
            this.playerData.replace(playerData.getId(), playerData);
            return;
        }

        this.playerData.put(playerData.getId(), playerData);
    }

    @Override
    public void unqueue(PlayerData playerData) {
        if(this.playerData.containsKey(playerData.getId())) {
            this.playerData.remove(playerData.getId());
        }
    }

    @Override
    public boolean isQueued(PlayerData object) {
        return this.playerData.containsKey(object.getId());
    }

    public boolean isPlayerSaving(int playerId) {
        return this.playerData.containsKey(playerId);
    }

    public PlayerData getPlayerData(int playerId) {
        return this.playerData.get(playerId);
    }

    @Override
    public void shutdown() {
        this.future.cancel(false);

        this.run();
    }

    public static PlayerDataStorageQueue getInstance() {
        if (instance == null) {
            instance = new PlayerDataStorageQueue();
        }

        return instance;
    }
}
