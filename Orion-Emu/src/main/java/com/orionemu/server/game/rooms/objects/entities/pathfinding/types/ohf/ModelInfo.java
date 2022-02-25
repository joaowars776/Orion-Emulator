package com.orionemu.server.game.rooms.objects.entities.pathfinding.types.ohf;

/**
 * Created by SpreedBlood on 2017-12-20.
 */
class ModelInfo {
    private byte[][] map;
    private int maxX;
    private int maxY;

    ModelInfo(int MaxX, int MaxY, byte[][] Map) {
        this.map = Map;
        this.maxX = MaxX;
        this.maxY = MaxY;
    }

    byte GetState(int x, int y) {
        if ((x >= this.maxX) || (x < 0)) {
            return 0;
        }
        if ((y >= this.maxY) || (y < 0)) {
            return 0;
        }
        return this.map[x][y];
    }
}