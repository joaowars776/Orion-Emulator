package com.orionemu.games.snowwar.gameobjects;

import com.orionemu.games.snowwar.Direction360;
import com.orionemu.games.snowwar.PlayerTile;
import com.orionemu.games.snowwar.Tile;

/*
 * ****************
 * @author capos *
 * ****************
 */

public abstract class PickBallsGameItemObject extends GameItemObject {
    protected int parentFuseId;
    protected int snowBalls;
    protected Tile location;

    public int concurrentUses;

    public PickBallsGameItemObject(int _arg1, Tile _arg2, int _arg3, int _arg4){
        super(_arg1);
        location = _arg2;
        snowBalls = _arg3;
        parentFuseId = _arg4;
    }

    @Override
	public Direction360 direction360(){
        return (null);
    }

    @Override
	public PlayerTile location3D(){
        return (location.location());
    }

    public int _4rk(){
        return (parentFuseId);
    }

    public boolean canPickUpFromHere(){
		return snowBalls > concurrentUses;
    }

    public int pickUp(int ammount){
        if (snowBalls < ammount) {
        	ammount = snowBalls;
        }
        onSnowballPickup(ammount);
        return (ammount);
    }

    public abstract void onSnowballPickup(int ammount);
}

