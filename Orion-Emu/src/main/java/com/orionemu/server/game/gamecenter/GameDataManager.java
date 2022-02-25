package com.orionemu.server.game.gamecenter;


import com.orionemu.server.storage.queries.gamecenter.GameCenterDao;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class GameDataManager {
    public Map<Integer, GameData> getGameData() {
        return gameData;
    }

    private Map<Integer, GameData> gameData;
    private static Logger log = Logger.getLogger(GameDataManager.class.getName());
    private static GameDataManager instance;

    public GameDataManager(){
        gameData = new HashMap<>();
    }

    public void initialize(){
        if(gameData != null){
            gameData.clear();
        }

        GameCenterDao.getGameData(gameData);
        log.info("Loaded " + this.gameData.size() + " game datas");

        log.info("GamedataManager initialized");
    }

    public GameData tryGetGame(int gameId){
        if(gameData.containsKey(gameId)){
            return gameData.get(gameId);
        }
        return null;
    }

    public static GameDataManager getInstance(){
        if(instance == null)
            instance = new GameDataManager();
        return instance;
    }
}
