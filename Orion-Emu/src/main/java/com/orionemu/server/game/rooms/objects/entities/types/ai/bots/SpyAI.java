package com.orionemu.server.game.rooms.objects.entities.types.ai.bots;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.rooms.objects.entities.RoomEntity;
import com.orionemu.server.game.rooms.objects.entities.RoomEntityType;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.objects.entities.types.ai.AbstractBotAI;
import com.orionemu.server.game.rooms.objects.entities.types.data.types.SpyBotData;

public class SpyAI extends AbstractBotAI {

    private boolean hasSaidYes = false;

    public SpyAI(RoomEntity entity) {
        super(entity);
    }

    @Override
    public boolean onPlayerEnter(PlayerEntity playerEntity) {
        if (playerEntity.getPlayerId() != this.getBotEntity().getData().getOwnerId()) {
            if (!playerEntity.getPlayer().isInvisible()) {
                if (!((SpyBotData) this.getBotEntity().getDataObject()).getVisitors().contains(playerEntity.getUsername())) {
                    ((SpyBotData) this.getBotEntity().getDataObject()).getVisitors().add(playerEntity.getUsername());
                }
            }
        } else {
            if(((SpyBotData) this.getBotEntity().getDataObject()).getVisitors().size() == 0) {
                this.getBotEntity().say(Locale.getOrDefault("orion.game.bot.spy.noVisitors", "There have been no visitors while you've been away!!!"));
                this.hasSaidYes = true;
            } else {
                this.getBotEntity().say(Locale.getOrDefault("orion.game.bot.spy.sayYes", "Nice to see you Sir! Please say yes if you'd like me to tell who have visited room while you've been gone."));
                this.hasSaidYes = false;
            }
        }

        return false;
    }

    @Override
    public boolean onTalk(PlayerEntity entity, String message) {
        if (this.hasSaidYes) {
            return false;
        }

        if (entity.getPlayerId() == this.getBotEntity().getData().getOwnerId()) {
            if (message.equals("yes") || message.equals("oui") || message.equals("sim") || message.equals("ya") || message.equals(Locale.getOrDefault("orion.game.bot.yes", "yes"))) {
                String stillIn = "";
                String left = "";

                for (String username : ((SpyBotData) this.getBotEntity().getDataObject()).getVisitors()) {
                    boolean isLast = ((SpyBotData) this.getBotEntity().getDataObject()).getVisitors().indexOf(username) == (((SpyBotData) this.getBotEntity().getDataObject()).getVisitors().size() - 1);

                    if (this.getBotEntity().getRoom().getEntities().getEntityByName(username, RoomEntityType.PLAYER) != null) {
                        if (isLast) {
                            stillIn += username + (stillIn.equals("") ? Locale.getOrDefault("orion.game.bot.spy.stillInRoom.single", " is still in the room") : Locale.getOrDefault("orion.game.bot.spy.stillInRoom.multiple", " are still in the room"));
                        } else {
                            stillIn += username + ", ";
                        }
                    } else {
                        if (isLast) {
                            left += username + (left.equals("") ? Locale.getOrDefault("orion.game.bot.spy.leftRoom.single", " has left") : Locale.getOrDefault("orion.game.bot.spy.leftRoom.multiple", " have left"));
                        } else {
                            left += username + ", ";
                        }
                    }
                }

                if(!left.equals("")) {
                    this.getBotEntity().say(left);
                }

                if(!stillIn.equals("")) {
                    this.getBotEntity().say(stillIn);
                }

                ((SpyBotData) this.getBotEntity().getDataObject()).getVisitors().clear();
                this.getBotEntity().saveDataObject();
                this.hasSaidYes = true;
            }
        }
        return false;
    }

    @Override
    public boolean onPlayerLeave(PlayerEntity entity) {
        if (entity.getPlayerId() == this.getBotEntity().getData().getOwnerId()) {
            this.hasSaidYes = false;
        }

        return false;
    }

    @Override
    public boolean onAddedToRoom() {
        this.getBotEntity().say(Locale.getOrDefault("orion.game.bot.spy.addedToRoom", "Hi! Next time you enter the room, I'll let you know who visited while you were away.."));
        return false;
    }
}
