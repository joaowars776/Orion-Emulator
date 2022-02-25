package com.orionemu.server.network.messages.incoming.gamecenter;

import com.orionemu.server.game.gamecenter.GameData;
import com.orionemu.server.game.gamecenter.GameDataManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.gamecenter.GameCenterGameListComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

import java.util.Map;

public class GetGameListingEvent implements Event {
    public void handle(Session session, MessageEvent msg){
        Map<Integer, GameData> data = GameDataManager.getInstance().getGameData();

        session.send(new GameCenterGameListComposer(data));
    }
}
