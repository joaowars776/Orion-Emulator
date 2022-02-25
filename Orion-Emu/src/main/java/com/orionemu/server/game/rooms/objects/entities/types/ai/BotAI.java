package com.orionemu.server.game.rooms.objects.entities.types.ai;

import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.types.mapping.RoomTile;
import com.orionemu.server.game.rooms.types.misc.ChatEmotion;


public interface BotAI {
    boolean onTalk(PlayerEntity entity, String message); // return value indicates whether the message should be broadcasted to room or not.

    boolean onPlayerEnter(PlayerEntity playerEntity);

    boolean onPlayerLeave(PlayerEntity playerEntity);

    boolean onAddedToRoom();

    boolean onRemovedFromRoom();

    void onTick();

    void onReachedTile(RoomTile tile);

    void onTickComplete();

    void sit();

    void lay();

    void setTicksUntilCompleteInSeconds(double seconds);

    void say(String message);

    void say(String message, ChatEmotion emotion);

    boolean canMove();
}
