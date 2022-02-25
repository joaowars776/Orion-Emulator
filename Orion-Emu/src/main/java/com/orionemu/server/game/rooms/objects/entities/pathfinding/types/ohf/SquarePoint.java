package com.orionemu.server.game.rooms.objects.entities.pathfinding.types.ohf;

/**
 * Created by SpreedBlood on 2017-12-20.
 */
class SquarePoint {
    private int x;
    private int y;
    private double distance;
    private byte squareData;
    private boolean inUse;
    private boolean override;
    private boolean lastStep;

    SquarePoint(int pX, int pY, int pTargetX, int pTargetY, byte SquareData, boolean pOverride) {
        this.x = pX;
        this.y = pY;
        this.squareData = SquareData;
        this.inUse = true;
        this.override = pOverride;
        this.distance = 0.0;
        this.lastStep = (pX == pTargetX) && (pY == pTargetY);
        this.distance = DreamPathFinder.getDistance(pX, pY, pTargetX, pTargetY);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    double getDistance() {
        return distance;
    }

    public byte getSquareData() {
        return squareData;
    }

    boolean isInUse() {
        return inUse;
    }

    public boolean isOverride() {
        return override;
    }

    public boolean isLastStep() {
        return lastStep;
    }

    boolean canWalk() {
        if (!this.lastStep) {
            return this.override || ((this.squareData == 1) || (this.squareData == 4));
        }
        if (!this.override) {
            if (this.squareData == 3) {
                return true;
            }
            if (this.squareData == 1) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
}