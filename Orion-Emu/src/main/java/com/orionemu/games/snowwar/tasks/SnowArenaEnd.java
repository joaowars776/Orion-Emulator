package com.orionemu.games.snowwar.tasks;

import com.orionemu.games.snowwar.SnowWar;
import com.orionemu.games.snowwar.SnowWarRoom;
import com.orionemu.games.snowwar.gameobjects.HumanGameObject;
import com.orionemu.server.network.messages.outgoing.gamecenter.snowwar.GameEndingComposer;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class SnowArenaEnd {
    public static void exec(SnowWarRoom room) {
        room.Winner = 0;
        int blueScore = 0;
        int redScore = 0;
        for (final int TeamId : SnowWar.TEAMS) {
            if (TeamId == SnowWar.TEAM_BLUE) {
                blueScore += room.TeamScore[TeamId - 1];
            } else {
                redScore += room.TeamScore[TeamId - 1];
            }
        }
        if (blueScore > redScore) {
            room.Winner = 1;
            room.Result = 1;
        } else if (redScore > blueScore){
            room.Winner = 2;
            room.Result = 1;
        } else if (redScore == blueScore) {
            room.Winner = 0;
            room.Result = 2;
        }

        for (final HumanGameObject player : room.players.values()) {
            if (room.MostHits == null) {
                room.MostHits = player;
            }
            if (room.MostKills == null) {
                room.MostKills = player;
            }
            if (player.hits > room.MostHits.hits) {
                room.MostHits = player;
            }
            if (player.kills > room.MostKills.kills) {
                room.MostKills = player;
            }

            if (player.team == room.Winner) {
                player.cn.getPlayer().increaseXP(50);
            } else {
                player.cn.getPlayer().increaseXP(25);
            }
        }

        room.broadcast(new GameEndingComposer(room));
    }
}
