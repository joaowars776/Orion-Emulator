package com.orionemu.server.network.messages.outgoing.messenger;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.players.components.types.messenger.MessengerFriend;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.Map;


public class MessengerConfigMessageComposer extends MessageComposer {

    private final boolean hasStaffChat;
    private final Map<Integer, MessengerFriend> friends;

    public MessengerConfigMessageComposer(Map<Integer, MessengerFriend> friends, boolean hasStaffChat) {
        this.hasStaffChat = hasStaffChat;
        this.friends = friends;
    }

    @Override
    public short getId() {
        return Composers.MessengerInitMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(1100); // TODO: put this stuff in static config somewhere :P
        msg.writeInt(300);
        msg.writeInt(800);
        msg.writeInt(1100);
        msg.writeInt(0);

        int counter = 0;

        for (Map.Entry<Integer, MessengerFriend> friend : friends.entrySet()) {
            if (friend.getValue() != null && friend.getValue().getSession() != null)
                counter++;
        }

        msg.writeInt(counter + (hasStaffChat ? 1 : 0));

        for (Map.Entry<Integer, MessengerFriend> friend : friends.entrySet()) {
            if (friend.getValue() != null) {
                friend.getValue().updateClient();
                friend.getValue().serialize(msg);
            }
        }

        if (hasStaffChat) {
            msg.writeInt(-1);
            msg.writeString("Staff chat");
            msg.writeInt(1);
            msg.writeBoolean(true);
            msg.writeBoolean(false);
            msg.writeString("hr-831-45.fa-1206-91.sh-290-1331.ha-3129-100.hd-180-2.cc-3039-73.ch-3215-92.lg-270-73");
            msg.writeInt(0);
            msg.writeString("");
            msg.writeString("");
            msg.writeString("");
            msg.writeBoolean(true);
            msg.writeBoolean(true);
            msg.writeBoolean(false);
            msg.writeBoolean(false);
            msg.writeBoolean(false);

        }
    }
}