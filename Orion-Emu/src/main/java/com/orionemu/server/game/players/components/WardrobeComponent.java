package com.orionemu.server.game.players.components;

import com.orionemu.server.game.players.components.types.wardrobe.WardrobeClothing;
import com.orionemu.server.game.players.types.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WardrobeComponent {
    private final Map<Integer, WardrobeClothing> purchasedClothing;

    private final Player player;

    public WardrobeComponent(final Player player) {
        this.player = player;

        this.purchasedClothing = new ConcurrentHashMap<>();

    }
}
