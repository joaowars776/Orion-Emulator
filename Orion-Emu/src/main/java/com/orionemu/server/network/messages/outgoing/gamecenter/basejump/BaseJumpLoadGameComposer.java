package com.orionemu.server.network.messages.outgoing.gamecenter.basejump;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.gamecenter.GameData;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class BaseJumpLoadGameComposer extends MessageComposer {
    Session session;
    private GameData gameData;

    public BaseJumpLoadGameComposer(Session session, GameData gameData){
        this.session = session;
        this.gameData = gameData;
    }

    @Override
    public short getId() {
        return Composers.BaseJumpLoadGameComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(gameData.getGameId());
        msg.writeString("1365260055982");
        msg.writeString(gameData.getResourcePath() + gameData.getGameSWF());
        msg.writeString("best");
        msg.writeString("showAll");
        msg.writeInt(60);
        msg.writeInt(10);
        msg.writeInt(8);
        msg.writeInt(6);
        msg.writeString("assetUrl");
        msg.writeString(gameData.getResourcePath() + gameData.getGameAssets());
        msg.writeString("habboHost");
        msg.writeString("http://fuseus-private-httpd-fe-1");
        msg.writeString("accessToken");
        msg.writeString(session.getPlayer().getSsoTicket());
        msg.writeString("gameServerHost");
        msg.writeString(gameData.getGameServerHost());
        msg.writeString("gameServerPort");
        msg.writeString(gameData.getGameServerPort());
        msg.writeString("socketPolicyPort");
        msg.writeString(gameData.getSocketPolicyPort());
    }
}