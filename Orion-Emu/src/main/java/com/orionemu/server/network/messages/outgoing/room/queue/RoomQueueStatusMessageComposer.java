package com.orionemu.server.network.messages.outgoing.room.queue;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

public class RoomQueueStatusMessageComposer extends MessageComposer {

    private final int playersWaiting;

    public RoomQueueStatusMessageComposer(int playersWaiting) {
        this.playersWaiting = playersWaiting;
    }

    @Override
    public short getId() {
        return 0;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(2);
        {
            msg.writeString("visitors");
            msg.writeInt(2);

            msg.writeInt(1);
            {
                msg.writeString("visitors");
                msg.writeInt(this.playersWaiting);
            }

            msg.writeString("spectators");
            msg.writeInt(1);

            msg.writeInt(1);
            {
                msg.writeString("spectators");
                msg.writeInt(0);
            }
        }

    }
}
