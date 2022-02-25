package com.orionemu.server.network.messages.incoming.gamecenter;

import com.orionemu.server.game.players.data.PlayerData;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.storage.queries.gamecenter.GameCenterDao;

import java.util.List;

/**
 * Created by admin on 2017-06-28.
 */
public class Game1WeeklyLeaderboardEvent implements Event {
    public void handle(Session session, MessageEvent msg){
        int gameId = msg.readInt();
        List<PlayerData> data = GameCenterDao.getLeaderBoard();
    }
}
