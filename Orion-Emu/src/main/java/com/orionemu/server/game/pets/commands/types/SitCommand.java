package com.orionemu.server.game.pets.commands.types;

import com.orionemu.server.game.pets.commands.PetCommand;
import com.orionemu.server.game.rooms.objects.entities.types.PetEntity;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;

public class SitCommand extends PetCommand {
    @Override
    public boolean execute(PlayerEntity executor, PetEntity entity) {
        entity.cancelWalk();
        entity.getPetAI().sit();
        return true;
    }

    @Override
    public int getRequiredLevel() {
        return 0;
    }

    @Override
    public boolean requiresOwner() {
        return true;
    }
}
