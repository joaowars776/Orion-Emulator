package com.orionemu.server.game.rooms.objects.entities.pathfinding.types.ohf;

/**
 * Created by SpreedBlood on 2017-12-20.
 */
class HeightInfo {
    private double[][] map;
    private int maxX;
    private int maxY;

    HeightInfo(int MaxX, int MaxY, double[][] Map) {
        this.map = Map;
        this.maxX = MaxX;
        this.maxY = MaxY;
    }

    double GetState(int x, int y) {
        if ((x >= this.maxX) || (x < 0)) {
            return 0.0;
        }
        if ((y >= this.maxY) || (y < 0)) {
            return 0.0;
        }
        return this.map[x][y];
    }
}
