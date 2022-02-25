package com.orionemu.server.game.tasks;

import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.tasks.OrionTask;

/**
 * Created by SpreedBlood on 2017-08-13.
 */
public class RoomUserWhisper implements OrionTask {
    private String message;
    private Session session;
    private PlayerEntity entity;

    public RoomUserWhisper(String message, Session session, PlayerEntity entity) {
        this.message = message;
        this.session = session;
        this.entity = entity;
    }

    @Override
    public void run() {
        session.send(new WhisperMessageComposer(entity.getId(), message, 30));
    }
}
