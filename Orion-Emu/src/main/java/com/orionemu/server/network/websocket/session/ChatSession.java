package com.orionemu.server.network.websocket.session;

import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.game.players.data.PlayerData;
import com.orionemu.server.storage.queries.webchat.WebChatDao;
import com.corundumstudio.socketio.SocketIOClient;

public class ChatSession {
    private SocketIOClient socketIOClient;
    private PlayerData playerData;

    public ChatSession(String authTicket, SocketIOClient webSocketClient) {
        this.socketIOClient = webSocketClient;

        int playerId = WebChatDao.findPlayerIdByAuthTicket(authTicket);

        if (playerId != 0) {
            this.playerData = PlayerManager.getInstance().getDataByPlayerId(playerId);
        }
    }

    public void dispose() {
        // send to our friends that we've gone offline!
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public SocketIOClient getSocketIOClient() {
        return socketIOClient;
    }
}
