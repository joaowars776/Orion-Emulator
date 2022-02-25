package com.orionemu.server.network.messages.incoming.camera;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

/**
 * Created by Emrik on 2017-06-19.
 */
public class CameraRoomPictureEvent implements Event {

    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {

    }
}
