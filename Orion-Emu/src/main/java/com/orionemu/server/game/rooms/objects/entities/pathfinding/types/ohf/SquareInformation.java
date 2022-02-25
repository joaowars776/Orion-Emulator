package com.orionemu.server.game.rooms.objects.entities.pathfinding.types.ohf;

/**
 * Created by SpreedBlood on 2017-12-20.
 */
class SquareInformation {
    private int x;
    private int y;
    private SquarePoint[] pos;
    private SquarePoint target;
    private SquarePoint point;

    SquareInformation(int pX, int pY, SquarePoint pTarget, ModelInfo pMap, boolean pUserOverride, boolean CalculateDiagonal) {
        this.x = pX;
        this.y = pY;
        this.target = pTarget;
        this.point = new SquarePoint(pX, pY, pTarget.getX(), pTarget.getY(), pMap.GetState(pX, pY), pUserOverride);
        this.pos = new SquarePoint[8];
        if (CalculateDiagonal) {
            this.pos[1] = new SquarePoint(pX - 1, pY - 1, pTarget.getX(), pTarget.getY(), pMap.GetState(pX - 1, pY - 1), pUserOverride);
            this.pos[3] = new SquarePoint(pX - 1, pY + 1, pTarget.getX(), pTarget.getY(), pMap.GetState(pX - 1, pY + 1), pUserOverride);
            this.pos[5] = new SquarePoint(pX + 1, pY + 1, pTarget.getX(), pTarget.getY(), pMap.GetState(pX + 1, pY + 1), pUserOverride);
            this.pos[7] = new SquarePoint(pX + 1, pY - 1, pTarget.getX(), pTarget.getY(), pMap.GetState(pX + 1, pY - 1), pUserOverride);
        }
        this.pos[0] = new SquarePoint(pX, pY - 1, pTarget.getX(), pTarget.getY(), pMap.GetState(pX, pY - 1), pUserOverride);
        this.pos[2] = new SquarePoint(pX - 1, pY, pTarget.getX(), pTarget.getY(), pMap.GetState(pX - 1, pY), pUserOverride);
        this.pos[4] = new SquarePoint(pX, pY + 1, pTarget.getX(), pTarget.getY(), pMap.GetState(pX, pY + 1), pUserOverride);
        this.pos[6] = new SquarePoint(pX + 1, pY, pTarget.getX(), pTarget.getY(), pMap.GetState(pX + 1, pY), pUserOverride);
    }

    SquarePoint Pos(int val) {
        return this.pos[val];
    }

    SquarePoint getPoint() {
        return this.point;
    }
}