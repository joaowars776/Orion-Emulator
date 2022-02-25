package com.orionemu.api.game.players.data.components.inventory;

import com.orionemu.api.game.furniture.types.FurnitureDefinition;
import com.orionemu.api.game.furniture.types.LimitedEditionItem;
import com.orionemu.api.networking.messages.IComposer;

public interface PlayerItem {
    long getId();

    FurnitureDefinition getDefinition();

    int getBaseId();

    String getExtraData();

    LimitedEditionItem getLimitedEditionItem();

    int getVirtualId();

    void compose(IComposer message);

    PlayerItemSnapshot createSnapshot();
}