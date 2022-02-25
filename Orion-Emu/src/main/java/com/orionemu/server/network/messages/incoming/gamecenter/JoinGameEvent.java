package com.orionemu.server.network.messages.incoming.gamecenter;

import com.orionemu.games.snowwar.SnowPlayerQueue;
import com.orionemu.server.game.gamecenter.GameData;
import com.orionemu.server.game.gamecenter.GameDataManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.gamecenter.basejump.BaseJumpJoinQueueComposer;
import com.orionemu.server.network.messages.outgoing.gamecenter.basejump.BaseJumpLoadGameComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

/**
 * Created by SpreedBlood on 2017-12-23.
 */
public class JoinGameEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int gameId = msg.readInt();
        GameData gameData = GameDataManager.getInstance().tryGetGame(gameId);
        if (gameData.getGameName().contains("basejump")) {
            client.send(new BaseJumpJoinQueueComposer(gameId));
            client.send(new BaseJumpLoadGameComposer(client, gameData));
        } else if(gameData.getGameName().contains("snowwar")) {
            SnowPlayerQueue.addPlayerInQueue(client);
        }
    }
}
