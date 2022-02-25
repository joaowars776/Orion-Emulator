package com.orionemu.server.game.rooms.objects.entities.pathfinding.types.ohf;

import com.orionemu.server.game.rooms.objects.misc.Position;
import com.orionemu.server.game.rooms.types.mapping.RoomMapping;

final class DreamPathFinder {

    private static SquarePoint getClosestSquare(SquareInformation pInfo, RoomMapping roomMapping) {
        double getDistance = pInfo.getPoint().getDistance();
        SquarePoint point = pInfo.getPoint();
        double state = roomMapping.getStepHeight(new Position(pInfo.getPoint().getX(), pInfo.getPoint().getY()));
        for (int i = 0; i < 8; i++) {
            SquarePoint point2 = pInfo.Pos(i);
            if ((point2.isInUse() && point2.canWalk()) && ((roomMapping.getStepHeight(new Position(point2.getX(), point2.getY())) - state) < 3.0)) {
                double num4 = point2.getDistance();
                if (getDistance > num4) {
                    getDistance = num4;
                    point = point2;
                }
            }
        }
        return point;
    }

    static SquarePoint getNextStep(int pUserX, int pUserY, int pUserTargetX, int pUserTargetY, byte[][] pGameMap, RoomMapping roomMapping, int MaxX, int MaxY, boolean pUserOverride, boolean pDiagonal) {
        ModelInfo pMap = new ModelInfo(MaxX, MaxY, pGameMap);
        SquarePoint pTarget = new SquarePoint(pUserTargetX, pUserTargetY, pUserTargetX, pUserTargetY, pMap.GetState(pUserTargetX, pUserTargetY), pUserOverride);
        if ((pUserX == pUserTargetX) && (pUserY == pUserTargetY)) {
            return pTarget;
        }
        SquareInformation pInfo = new SquareInformation(pUserX, pUserY, pTarget, pMap, pUserOverride, pDiagonal);
        return getClosestSquare(pInfo, roomMapping);
    }

    static double getDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((double) (x1 - x2), 2.0) + Math.pow((double) (y1 - y2), 2.0));
    }

}
