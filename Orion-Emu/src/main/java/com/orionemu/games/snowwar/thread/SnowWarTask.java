package com.orionemu.games.snowwar.thread;

import com.orionemu.games.snowwar.SnowPlayerQueue;
import com.orionemu.games.snowwar.SnowWar;
import com.orionemu.games.snowwar.SnowWarRoom;
import com.orionemu.games.snowwar.data.SnowWarPlayerData;
import com.orionemu.games.snowwar.gameobjects.HumanGameObject;
import com.orionemu.games.snowwar.tasks.*;
import com.orionemu.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.orionemu.server.network.sessions.Session;

import java.util.concurrent.ScheduledFuture;

public class SnowWarTask extends GameTask {
    public static void addTask(final GameTask task, final int initDelay, final int repeatDelay) {
        WorkerTasks.addTask(task, initDelay, repeatDelay, WorkerTasks.SnowWarTasks);
    }

    public SnowWarRoom room;

    public SnowWarTask(final SnowWarRoom snowRoom) {
        room = snowRoom;
    }

    @Override
    public void run() {
        try {
            if (room.STATUS == SnowWar.ARENA_END) {
                future.cancel(false);
                SnowArenaEnd.exec(room);
                return;
            }

            if (room.STATUS == SnowWar.ARENA) {
                SnowArenaRun.exec(room);
                return;
            }

            if (room.STATUS == SnowWar.STAGE_RUNNING) {
                SnowStageRun.exec(room);
                room.STATUS = SnowWar.ARENA;
                return;
            }

            if (room.STATUS == SnowWar.STAGE_STARTING) {
                SnowStageStarting.exec(room);
                room.STATUS = SnowWar.STAGE_RUNNING;
                SnowWarTask.addTask(this, 6000, SnowWar.GAMETURNMILLIS);
                return;
            }

            if (room.STATUS == SnowWar.STAGE_LOADING) {
                SnowStageLoading.exec(room);

                if (room.STATUS == SnowWar.STAGE_STARTING) {
                    future.cancel(false);
                    SnowWarTask.addTask(this, 6000, 0);
                }
                return;
            }

            if (room.STATUS == SnowWar.TIMER_TOLOBBY) {
                if (room.TimeToStart-- == 0) {
                    future.cancel(false);
                    SnowPlayerQueue.roomLoaded(room);
                    room.STATUS = SnowWar.STAGE_LOADING;
                    SnowWarTask.addTask(this, 100, 200);
                }
            }
        }
        catch(final Exception ex) {
            future.cancel(false);
            ex.printStackTrace();
            System.out.println("SnowEngine " + ex);
        }
    }
}

abstract class GameTask extends Thread {
    public ScheduledFuture<?> future;
}