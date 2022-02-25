package com.orionemu.games;

import com.orionemu.games.snowwar.data.SnowWarPlayerData;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class GamesLeaderboard {
    public static final Map<Integer, GamesLeaderboard> leaderboards = new ConcurrentHashMap<Integer, GamesLeaderboard>();

    public final int gameId;

    public List<SnowWarPlayerData> rankedList;

    public GamesLeaderboard(int id) {
        gameId = id;
    }
}
