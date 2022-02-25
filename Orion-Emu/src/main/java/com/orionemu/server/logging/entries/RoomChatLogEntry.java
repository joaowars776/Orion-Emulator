package com.orionemu.server.logging.entries;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.logging.AbstractLogEntry;
import com.orionemu.server.logging.LogEntryType;
import com.orionemu.server.storage.queries.player.PlayerDao;
import com.orionemu.server.utilities.TimeSpan;


public class RoomChatLogEntry extends AbstractLogEntry {
    private int roomId;
    private int userId;
    private String message;
    private int timestamp;

    public RoomChatLogEntry(int roomId, int userId, String message) {
        this.roomId = roomId;
        this.userId = userId;
        this.message = message;
        this.timestamp = (int) Orion.getTime();
    }

    public RoomChatLogEntry(int roomId, int userId, String message, int timestamp) {
        this.roomId = roomId;
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public void compose(IComposer msg) {
        msg.writeString(TimeSpan.millisecondsToDate((int)(Orion.getTime() - getTimestamp()) * 1000));

        msg.writeInt(this.getPlayerId());
        msg.writeString(PlayerDao.getUsernameByPlayerId(this.getPlayerId()));
        msg.writeString(this.getString());
        msg.writeBoolean(false);
    }

    @Override
    public LogEntryType getType() {
        return LogEntryType.ROOM_CHATLOG;
    }

    @Override
    public String getString() {
        return this.message;
    }

    @Override
    public int getTimestamp() {
        return this.timestamp;
    }

    @Override
    public int getRoomId() {
        return this.roomId;
    }

    @Override
    public int getPlayerId() {
        return this.userId;
    }
}
