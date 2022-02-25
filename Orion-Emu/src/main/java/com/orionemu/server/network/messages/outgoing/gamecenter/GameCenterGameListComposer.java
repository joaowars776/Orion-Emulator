package com.orionemu.server.network.messages.outgoing.gamecenter;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.gamecenter.GameData;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.Map;

public class GameCenterGameListComposer extends MessageComposer {
    private Map<Integer, GameData> data;

    public GameCenterGameListComposer(Map<Integer, GameData> data){
        this.data = data;
    }

    @Override
    public short getId() {
        return Composers.GameCenterGameListComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(data.size());
        for (GameData gameData : data.values()){
            msg.writeInt(gameData.getGameId());
            msg.writeString(gameData.getGameName());
            msg.writeString(gameData.getColourOne());
            msg.writeString(gameData.getColourTwo());
            msg.writeString(gameData.getResourcePath());
            msg.writeString("");
        }
    }
}