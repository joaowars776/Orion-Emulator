package com.orionemu.server.game.rooms.objects.items.types.floor;

import com.orionemu.api.networking.sessions.SessionManagerAccessor;
import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.game.players.types.Player;
import com.orionemu.server.game.rooms.objects.entities.RoomEntity;
import com.orionemu.server.game.rooms.objects.items.RoomItemFactory;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;

import java.util.Random;


public class DiceFloorItem extends RoomItemFloor {
    private boolean isInUse = false;
    private int rigNumber = -1;
    private RoomEntity entity = null;

    public DiceFloorItem(long id, int itemId, Room room, int owner, String ownerName, int x, int y, double z, int rotation, String data) {
        super(id, itemId, room, owner, ownerName, x, y, z, rotation, data);
    }

    @Override
    public boolean onInteract(RoomEntity entity, int requestData, boolean isWiredTrigger) {
        this.entity = entity;
        if (!isWiredTrigger) {
            if (!this.getPosition().touching(entity)) {
                entity.moveTo(this.getPosition().squareInFront(this.rotation).getX(), this.getPosition().squareBehind(this.rotation).getY());
                return false;
            }
        }

        if (this.isInUse) {
            return false;
        }

        if (requestData >= 0) {
            if (!"-1".equals(this.getExtraData())) {
                this.setExtraData("-1");
                this.sendUpdate();

                this.isInUse = true;

                if (entity != null) {
                    if (entity.hasAttribute("diceRoll")) {
                        this.rigNumber = (int) entity.getAttribute("diceRoll");

                        entity.removeAttribute("diceRoll");
                    }
                }

                this.setTicks(RoomItemFactory.getProcessTime(2.5));
            }
        } else {
            this.setExtraData("0");
            this.sendUpdate();

            this.saveData();
        }

        return true;
    }

    @Override
    public void onPlaced() {
        if (!"0".equals(this.getExtraData())) {
            this.setExtraData("0");
        }
    }

    @Override
    public void onPickup() {
        this.cancelTicks();
    }

    @Override
    public void onTickComplete() {
        int num = new Random().nextInt(6) + 1;
        int interactingPlayerId = PlayerManager.getInstance().getPlayerIdByUsername(entity.getUsername());

        Session session = (Session)SessionManagerAccessor.getInstance().getSessionManager().getByPlayerId(interactingPlayerId);
        Player player = session.getPlayer();

        if(player.getData().getLira()) {
            player.getData().setCurrentNumber(num + player.getData().getCurrentNumber());
            player.getData().setOldNumber(player.getData().getCurrentNumber());
            session.send(new WhisperMessageComposer(player.getId(), "Jag slog en " + num + "a, jag har nu totalt " + player.getData().getCurrentNumber(), 34 ));

            if(player.getData().getCurrentNumber() > 21) {
                session.send(new WhisperMessageComposer(player.getId(), "Jag är sämst och förlorade...", 34 ));

                player.getData().setOldNumber(0);
                player.getData().setCurrentNumber(0);
            }
        }

        this.setExtraData(Integer.toString(this.rigNumber == -1 ? num : this.rigNumber));


        this.sendUpdate();

        this.saveData();

        if (this.rigNumber != -1) this.rigNumber = -1;

        this.isInUse = false;
    }
}
