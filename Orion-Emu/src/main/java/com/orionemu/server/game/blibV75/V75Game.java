package com.orionemu.server.game.blibV75;

import com.orionemu.server.game.players.types.Player;
import com.orionemu.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.orionemu.server.game.rooms.objects.entities.types.PetEntity;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.objects.items.types.floor.GateFloorItem;
import com.orionemu.server.game.rooms.objects.items.types.floor.banzai.JackpotTimerFloorItem;
import com.orionemu.server.game.rooms.objects.items.types.floor.pet.HorseWalkOnItem;
import com.orionemu.server.game.rooms.objects.misc.Position;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.game.rooms.types.components.games.GameType;
import com.orionemu.server.game.rooms.types.components.games.V75AbstractGame;
import com.orionemu.server.game.rooms.types.mapping.RoomTile;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;

import java.util.Map;

/**
 * Created by SpreedBlood on 2017-08-11.
 */
public class V75Game extends V75AbstractGame {
    private String horseWon = "";
    public V75Game(Room room) {
        super(room, GameType.V75);
    }

    @Override
    public void tick() {
        for (RoomItemFloor item : room.getItems().getByClass(JackpotTimerFloorItem.class)) {
            item.setExtraData((gameLength - timer) + "");
            item.sendUpdate();
        }

        for (PetEntity entity : this.room.getEntities().getPetEntities()){
            entity.getPetAI().walkNow();
            for (RoomItemFloor floor : this.room.getItems().getByClass(HorseWalkOnItem.class)){
                if((entity.getPosition().getX() == floor.getPosition().getX()) && (entity.getPosition().getY() == floor.getPosition().getY())){
                    this.horseWon = entity.getData().getName();
                    this.onGameEnds();
                    RoomTile tile = this.room.getMapping().getTile(floor.getPosition().getX(), floor.getPosition().getY());
                    tile.reload();
                    tile.getEntities().remove(entity);
                }
            }
        }
    }

    @Override
    public void onGameStarts() {
        for(GateFloorItem item : this.room.getItems().getByClass(GateFloorItem.class)){
            item.toggleInteract(true);
            item.sendUpdate();
            item.saveData();
            RoomTile tile = this.room.getMapping().getTile(item.getPosition().getX(), item.getPosition().getY());
            tile.reload();
        }
    }

    @Override
    public void onGameEnds() {
        StringBuilder winners = new StringBuilder();

        for(Map.Entry<BlibV75, String> entry : this.getGameComponent().getV75StringMap().entrySet()){
            if(entry.getValue().equals(horseWon.toLowerCase())){
                BlibV75 winner = entry.getKey();
                int betAmount = winner.getAmount();
                Session session = winner.getSession();
                Player player = session.getPlayer();
                int winAmount = betAmount * 3;

                player.getData().increaseCredits(winAmount);
                session.send(new WhisperMessageComposer(player.getId(), "Du vann " + winAmount + " mynt!", 30));
                player.sendBalance();
                player.getData().save();
                winners.append(winner.getUsername());
                winners.append(", ");
            } else {
                BlibV75 looser = entry.getKey();
                looser.getSession().send(new WhisperMessageComposer(looser.getSession().getPlayer().getId(), "Du förlorade " + looser.getAmount() + " mynt.", 30));
            }
        }
        this.room.getEntities().broadcastMessage(new WhisperMessageComposer(1, "Hästen: " + horseWon + ". " + "Vinnarna är: " + winners.toString() + "!", 30));
        for(PetEntity entity : this.room.getEntities().getPetEntities()) {
            Position position = null;
            switch (entity.getData().getName().toLowerCase()){
                case "lisa":
                    position = new Position(15, 26);
                    entity.applyEffect(new PlayerEffect(4, 5));
                    entity.cancelWalk();
                    entity.warp(position);
                    break;
                case "kalle":
                    position = new Position(13, 26);
                    entity.applyEffect(new PlayerEffect(4, 5));
                    entity.cancelWalk();
                    entity.warp(position);
                    break;
                case "sten":
                    position = new Position(11, 26);
                    entity.applyEffect(new PlayerEffect(4, 5));
                    entity.cancelWalk();
                    entity.warp(position);
                    break;
                case "zlatan":
                    position = new Position(9, 26);
                    entity.applyEffect(new PlayerEffect(4, 5));
                    entity.cancelWalk();
                    entity.warp(position);
                    break;
            }
        }
        for(GateFloorItem item : this.room.getItems().getByClass(GateFloorItem.class)){
            item.toggleInteract(true);
            item.sendUpdate();
            item.saveData();

            RoomTile tile = this.room.getMapping().getTile(item.getPosition().getX(), item.getPosition().getY());

            tile.reload();
        }
        this.getGameComponent().setV75Active(false);
        this.getGameComponent().getV75StringMap().clear();
        this.getGameComponent().getV75Usernames().clear();
        this.stop();
        this.getGameComponent().stopV75();
    }
}
