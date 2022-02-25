package com.orionemu.server.network.messages.outgoing.moderation;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.logging.entries.RoomChatLogEntry;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.List;


public class ModToolRoomChatlogMessageComposer extends MessageComposer {

    private final static String ROOM_ID = "roomId";
    private final static String ROOM_NAME = "roomName";

    private final int roomId;
    private final String roomName;

    private final List<RoomChatLogEntry> roomChatLogEntries;

    public ModToolRoomChatlogMessageComposer(final int roomId, final String roomName, final List<RoomChatLogEntry> chatLogs) {
        this.roomId = roomId;
        this.roomName = roomName;

        this.roomChatLogEntries = chatLogs;
    }

    @Override
    public short getId() {
        return Composers.ModeratorRoomChatlogMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeByte(1);
        msg.writeShort(3);
        msg.writeString("");

        msg.writeByte(0);
        msg.writeBoolean(false); // Is public

        msg.writeString(ROOM_ID);
        msg.writeByte(1);
        msg.writeInt(roomId);

        msg.writeString(ROOM_NAME);
        msg.writeByte(2);
        msg.writeString(roomName);

        msg.writeShort(roomChatLogEntries.size());

        for (RoomChatLogEntry entry : roomChatLogEntries) {
            entry.compose(msg);
        }
    }

    @Override
    public void dispose() {
        this.roomChatLogEntries.clear();
    }
}