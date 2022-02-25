package com.orionemu.server.network.websocket.listeners;

import com.orionemu.server.network.websocket.session.ChatSession;
import com.orionemu.server.network.websocket.session.ChatSessionStore;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;

public class DisconnectionListener implements DisconnectListener {

    @Override
    public void onDisconnect(SocketIOClient socketIOClient) {
        final ChatSession chatSession = ChatSessionStore.getInstance().getChatSession(socketIOClient.getSessionId());

        if (chatSession != null) {
            chatSession.dispose();
            ChatSessionStore.getInstance().remove(socketIOClient.getSessionId());
        }
    }
}
