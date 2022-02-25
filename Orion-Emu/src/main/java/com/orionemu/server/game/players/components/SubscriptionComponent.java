package com.orionemu.server.game.players.components;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.game.players.types.Player;
import com.orionemu.server.game.players.types.PlayerComponent;


public class SubscriptionComponent implements PlayerComponent {
    private Player player;

    private boolean hasSub;
    private int expire;

    public SubscriptionComponent(Player player) {
        this.player = player;

        this.load();
    }

    public void load() {
        this.hasSub = true;
        this.expire = (int) Orion.getTime() + 315569260;

        // TODO: Subscriptions
    }

    public void add(int days) {
        // TODO: Add or extend the subscription
    }

    public void delete() {
        this.hasSub = false;
        this.expire = 0;
    }

    @Override
    public void dispose() {

    }

    public boolean isValid() {
        if (this.getExpire() <= Orion.getTime()) {
            return false;
        }

        return true;
    }

    public boolean exists() {
        return this.hasSub;
    }

    public int getExpire() {
        return this.expire;
    }

    public Player getPlayer() {
        return this.player;
    }
}
