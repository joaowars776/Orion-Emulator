package com.orionemu.server.network.messages.outgoing.camera;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;

/**
 * Created by Emrik on 2017-06-19.
 */
public class CameraRoomThumbnailSavedComposer extends MessageComposer
{
    @Override
    public short getId() {
        return 0;
    }

    @Override
    public void compose(IComposer msg) {

    }
}
