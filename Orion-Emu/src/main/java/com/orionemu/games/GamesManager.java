package com.orionemu.games;

import com.orionemu.games.snowwar.SnowWar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class GamesManager {

    public static List<GameBase> games = new ArrayList<GameBase>();

    public static void initManager() {
        addGame(new SnowWar(), true);
    }

    public static void addGame(GameBase game, boolean enabled) {
        games.add(game);
        game.isEnabled = enabled;
    }

}
