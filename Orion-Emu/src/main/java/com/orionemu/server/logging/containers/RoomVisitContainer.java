package com.orionemu.server.logging.containers;

import com.orionemu.server.logging.database.queries.LogQueries;
import com.orionemu.server.logging.entries.RoomVisitLogEntry;
import com.orionemu.server.network.sessions.Session;

import java.util.List;


public class RoomVisitContainer {
    public RoomVisitLogEntry put(int playerId, int roomId, long timeEnter) {
        return LogQueries.putRoomVisit(playerId, roomId, (int) timeEnter);
    }

    public void updateExit(RoomVisitLogEntry logEntry) {
        LogQueries.updateRoomEntry(logEntry);
    }

    public List<RoomVisitLogEntry> get(int playerId, int count) {
        return null;
    }


    public void get(int playerid, Session session) {
        playerid = session.getPlayer().getData().getId();
    }
}
