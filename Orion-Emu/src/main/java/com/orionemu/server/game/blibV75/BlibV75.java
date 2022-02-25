package com.orionemu.server.game.blibV75;

import com.orionemu.api.networking.sessions.SessionManagerAccessor;
import com.orionemu.server.network.sessions.Session;

/**
 * Created by SpreedBlood on 2017-08-11.
 */
public class BlibV75 {
    private String username;
    private int amount;

    public BlibV75(String username, int amount) {
        this.username = username;
        this.amount = amount;
    }

    public Session getSession(){
        return (Session) SessionManagerAccessor.getInstance().getSessionManager().getByPlayerUsername(this.username);
    }

    public String getUsername() {
        return this.username;
    }

    public int getAmount() {
        return this.amount;
    }
}
