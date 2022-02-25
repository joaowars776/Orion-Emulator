package com.orionemu.server.network.messages.outgoing.moderation;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.types.RoomData;
import com.orionemu.server.logging.entries.RoomVisitLogEntry;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;
import org.joda.time.DateTime;

import java.util.List;


public class ModToolRoomVisitsMessageComposer extends MessageComposer {
    private final int playerId;
    private final String playerUsername;
    private final List<RoomVisitLogEntry> roomVisitLogEntries;

    public ModToolRoomVisitsMessageComposer(final int playerId, final String playerUsername, final List<RoomVisitLogEntry> roomVisits) {
        this.playerId = playerId;
        this.playerUsername = playerUsername;
        this.roomVisitLogEntries = roomVisits;
    }

    @Override
    public short getId() {
        return Composers.ModeratorUserRoomVisitsMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(playerId);
        msg.writeString(playerUsername);

        msg.writeInt(roomVisitLogEntries.size());

        for (RoomVisitLogEntry roomVisit : roomVisitLogEntries) {
            RoomData roomData = RoomManager.getInstance().getRoomData(roomVisit.getRoomId());
            DateTime dateTime = new DateTime(roomVisit.getEntryTime() * 1000L);

            msg.writeInt(roomData == null ? 0 : roomData.getId());
            msg.writeString(roomData == null ? "Unknown Room" : roomData.getName());

            msg.writeInt(dateTime.hourOfDay().get());
            msg.writeInt(dateTime.getMinuteOfHour());
        }
    }

    @Override
    public void dispose() {
        this.roomVisitLogEntries.clear();
    }
}
