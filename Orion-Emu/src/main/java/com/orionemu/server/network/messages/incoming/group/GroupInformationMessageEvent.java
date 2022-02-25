package com.orionemu.server.network.messages.incoming.group;

import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class GroupInformationMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int groupId = msg.readInt();
        boolean flag = msg.readBoolean();

        Group group = GroupManager.getInstance().get(groupId);

        if (group == null)
            return;

        client.send(group.composeInformation(flag, client.getPlayer().getId()));
    }
}
