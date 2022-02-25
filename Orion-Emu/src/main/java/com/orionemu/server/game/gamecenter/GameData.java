package com.orionemu.server.game.gamecenter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameData {
    private int GameId;
    private String GameName;

    public int getGameId() {
        return GameId;
    }

    public String getGameName() {
        return GameName;
    }

    public String getColourOne() {
        return ColourOne;
    }

    public String getColourTwo() {
        return ColourTwo;
    }

    public String getResourcePath() {
        return ResourcePath;
    }

    public String getStringThree() {
        return StringThree;
    }

    public String getGameSWF() {
        return GameSWF;
    }

    public String getGameAssets() {
        return GameAssets;
    }

    public String getGameServerHost() {
        return GameServerHost;
    }

    public String getGameServerPort() {
        return GameServerPort;
    }

    public String getSocketPolicyPort() {
        return SocketPolicyPort;
    }

    public boolean isGameEnabled() {
        return GameEnabled;
    }

    private String ColourOne;
    private String ColourTwo;
    private String ResourcePath;
    private String StringThree;
    private String GameSWF;
    private String GameAssets;
    private String GameServerHost;
    private String GameServerPort;
    private String SocketPolicyPort;
    private boolean GameEnabled;

    public GameData(int GameId, String GameName, String ColourOne, String ColourTwo, String ResourcePath, String StringThree, String GameSWF, String GameAssets, String GameServerHost, String GameServerPort, String SocketPolicyPort, Boolean GameEnabled)
    {
        this.GameId = GameId;
        this.GameName = GameName;
        this.ColourOne = ColourOne;
        this.ColourTwo = ColourTwo;
        this.ResourcePath = ResourcePath;
        this.StringThree = StringThree;
        this.GameSWF = GameSWF;
        this.GameAssets = GameAssets;
        this.GameServerHost = GameServerHost;
        this.GameServerPort = GameServerPort;
        this.SocketPolicyPort = SocketPolicyPort;
        this.GameEnabled = GameEnabled;
    }

    public GameData(ResultSet set) throws SQLException {
        this(set.getInt("id"),
        set.getString("game_name"),
        set.getString("color_one"),
        set.getString("color_two"),
        set.getString("resource_path"),
        set.getString("string_three"),
        set.getString("game_swf"),
        set.getString("game_assets"),
        set.getString("game_server_host"),
        set.getString("game_server_port"),
        set.getString("socket_policy_port"),
        set.getBoolean("game_enabed"));
    }
}
