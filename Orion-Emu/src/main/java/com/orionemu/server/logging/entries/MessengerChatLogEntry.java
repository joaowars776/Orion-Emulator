package com.orionemu.server.logging.entries;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.logging.AbstractLogEntry;
import com.orionemu.server.logging.LogEntryType;

public class MessengerChatLogEntry extends AbstractLogEntry {
    private int senderId;
    private int receiverId;
    private String message;
    private int timestamp;

    public MessengerChatLogEntry(int senderId, int receiverId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.timestamp = (int) Orion.getTime();
    }

    @Override
    public LogEntryType getType() {
        return LogEntryType.MESSENGER_CHATLOG;
    }

    @Override
    public String getString() {
        return "To: " + this.receiverId + ", Message: " + this.message;
    }

    @Override
    public int getTimestamp() {
        return this.timestamp;
    }

    @Override
    public int getPlayerId() {
        return this.senderId;
    }
}
