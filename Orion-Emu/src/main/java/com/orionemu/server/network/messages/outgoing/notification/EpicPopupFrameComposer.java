package com.orionemu.server.network.messages.outgoing.notification;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

/**
 * Created by Emrik on 2017-08-05.
 */
public class EpicPopupFrameComposer extends MessageComposer {
    private String assetURl;

    public EpicPopupFrameComposer(String assetURL) {
        this.assetURl= assetURL;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeString(assetURl);
    }

    @Override
    public short getId() { return Composers.HotelViewGoalComposer; }
}
