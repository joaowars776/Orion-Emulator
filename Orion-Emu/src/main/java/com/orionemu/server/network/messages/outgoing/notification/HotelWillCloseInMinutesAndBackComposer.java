package com.orionemu.server.network.messages.outgoing.notification;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

/**
 * Created by Emrik on 2017-08-05.
 */
public class HotelWillCloseInMinutesAndBackComposer extends MessageComposer {
    private int closeInMinutes;
    private int ReOpenInMinutes;

    public HotelWillCloseInMinutesAndBackComposer(int closeInMinutes, int ReOpenInMinutes)
    {
        this.closeInMinutes = closeInMinutes;
        this.ReOpenInMinutes= ReOpenInMinutes;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(true);
        msg.writeInt(closeInMinutes);
        msg.writeInt(ReOpenInMinutes);

    }

    @Override
    public short getId() { return Composers.HotelWillCloseInMinutesAndBackComposer; }
}
