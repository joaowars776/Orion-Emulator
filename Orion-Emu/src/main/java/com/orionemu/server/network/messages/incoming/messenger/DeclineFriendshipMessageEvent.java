package com.orionemu.server.network.messages.incoming.messenger;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.messenger.MessengerDao;


public class DeclineFriendshipMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final boolean allRequests = msg.readBoolean();
        final int mode = msg.readInt();

        if (allRequests) {
            MessengerDao.deleteRequestDataByRecieverId(client.getPlayer().getId());
            client.getPlayer().getMessenger().clearRequests();
            return;
        }

        final int sender = msg.readInt();
        final Integer messengerRequest = client.getPlayer().getMessenger().getRequestBySender(sender);

        if (messengerRequest != null) {
            MessengerDao.deleteRequestData(messengerRequest, client.getPlayer().getId());
            client.getPlayer().getMessenger().removeRequest(messengerRequest);
        }
    }
}
