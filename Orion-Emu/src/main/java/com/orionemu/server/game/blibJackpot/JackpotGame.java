package com.orionemu.server.game.blibJackpot;

import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.objects.items.types.floor.banzai.JackpotTimerFloorItem;
import com.orionemu.server.game.rooms.objects.items.types.floor.wired.triggers.WiredTriggerGameStarts;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.game.rooms.types.components.games.GameType;
import com.orionemu.server.game.rooms.types.components.games.RoomGame;
import com.orionemu.server.game.rooms.types.misc.ChatEmotion;
import com.orionemu.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.avatar.TalkMessageComposer;

import java.util.Random;

/**
 * Created by Emrik on 2017-07-22.
 */
public class JackpotGame extends RoomGame {
    private int jackpotBettingUsers = 0;

    public JackpotGame(Room room) {
        super(room, GameType.JACKPOT);
    }

    @Override
    public void tick() {
        for (RoomItemFloor item : room.getItems().getByClass(JackpotTimerFloorItem.class)) {
            item.setExtraData((gameLength - timer) + "");
            item.sendUpdate();
        }

        for (int i = 0; i < this.getGameComponent().getBetters().size(); i++) {
            jackpotBettingUsers++;
        }
    }

    @Override
    public void onGameStarts() {
        if (this.getGameComponent().getBetters() == null || !(this.getGameComponent().getBetters().size() > 0)) {
            this.timer = this.gameLength;
            this.room.getEntities().broadcastMessage(new AlertMessageComposer("Det finns ingen aktiv pot. Skriv :pot %mängd% för att starta."));
        }

        WiredTriggerGameStarts.executeTriggers(this.room);
    }

    @Override
    public void onGameEnds() {
        Random random = new Random();
        int totalBetting = 0;

        BlibJackpot winner = JackpotManager.getInstance().getWeightedRandom(this.getGameComponent().getBetters(), random);

        for (BlibJackpot jackpot : this.getGameComponent().getBetters().keySet()) {
            totalBetting += jackpot.getAmount();
        }

        if (winner != null) {
            this.getGameComponent().getBetters().clear();
            this.getGameComponent().getBettersUsernames().clear();
            this.room.getEntities().broadcastMessage(new TalkMessageComposer(winner.getSession().getPlayer().getData().getId(), "Potten var: " + totalBetting, ChatEmotion.NONE, 34));
            ;
            this.room.getEntities().broadcastMessage(new TalkMessageComposer(winner.getSession().getPlayer().getData().getId(), "Vinnaren är: " + winner.getUsername(), ChatEmotion.NONE, 34));
            ;
            winner.getSession().getPlayer().getData().increaseCredits(totalBetting);
            winner.getSession().getPlayer().sendBalance();
            winner.getSession().getPlayer().getData().save();
        }

        this.getGameComponent().setActive(false);
    }
}