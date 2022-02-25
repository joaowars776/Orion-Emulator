package com.orionemu.server.game.blibJackpot;

import com.orionemu.api.networking.sessions.SessionManagerAccessor;
import com.orionemu.server.network.sessions.Session;

/**
 * Created by Emrik on 2017-07-22.
 */
public class BlibJackpot {
    private String username;
    private int amount;

    public BlibJackpot(String username, int amount){
        this.username = username;
        this.amount = amount;
    }

    public Session getSession(){
        return (Session) SessionManagerAccessor.getInstance().getSessionManager().getByPlayerUsername(this.username);
    }

    public String getUsername() { return this.username; }

    public int getAmount() { return this.amount; }
}
