package com.orionemu.server.game.rooms.types.components.games;

import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.game.rooms.types.components.GameComponent;
import com.orionemu.server.tasks.OrionTask;
import com.orionemu.server.tasks.OrionThreadManager;
import org.apache.log4j.Logger;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public abstract class RoomGame implements OrionTask {
    private GameType type;

    protected int timer;
    protected int gameLength;
    protected boolean active;
    protected boolean finished = false;
    protected Room room;

    private ScheduledFuture future;
    private Logger log;

    public RoomGame(Room room, GameType gameType) {
        this.type = gameType;
        this.log = Logger.getLogger("RoomGame [" + room.getData().getName() + "][" + room.getData().getId() + "][" + this.type + "]");
        this.room = room;
    }

    @Override
    public void run() {
        try {
            if (timer == 0) {
                onGameStarts();
            }

            tick();

            if (timer >= gameLength) {
                onGameEnds();
                room.getGame().stop();
            }

            timer++;
        } catch (Exception e) {
            log.error("Error during game process", e);
        }
    }

    public void stop() {
        if (this.active && this.future != null) {
            this.future.cancel(false);

            this.active = false;
            this.gameLength = 0;
            this.timer = 0;
        }
    }

    public void startTimer(int amount) {
        if (this.active && this.future != null) {
            this.future.cancel(false);
        }

        this.future = OrionThreadManager.getInstance().executePeriodic(this, 0, 1, TimeUnit.SECONDS);

        this.gameLength = amount;
        this.active = true;

        log.debug("Game active for " + amount + " seconds");
    }

    public void increaseTimmer() {
        this.gameLength = this.gameLength + 5;
    }

    protected GameComponent getGameComponent() {
        return this.room.getGame();
    }

    public abstract void tick();

    public abstract void onGameEnds();

    public abstract void onGameStarts();

    public GameType getType() {
        return this.type;
    }

    public Logger getLog() {
        return this.log;
    }
}
