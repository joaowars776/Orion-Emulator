package com.orionemu.games;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public class GameBase {
    public boolean isEnabled;

    public final int gameId;
    public final String gameName;
    public final String bgColor;
    public final String textColor;
    public final String imagesPath;

    public GameBase(int id, String code, String bgcolor, String textcolor, String imagespath) {
        gameId = id;
        gameName = code;
        bgColor = bgcolor;
        textColor = textcolor;
        imagesPath = imagespath;

        GamesLeaderboard.leaderboards.put(gameId, new GamesLeaderboard(gameId));
    }
}
