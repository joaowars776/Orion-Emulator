package com.orionemu.server.game.rooms.objects.entities.pathfinding.types.ohf;

import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.game.rooms.objects.entities.RoomEntity;
import com.orionemu.server.game.rooms.objects.entities.RoomEntityStatus;
import com.orionemu.server.game.rooms.objects.entities.pathfinding.Square;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.objects.misc.Position;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.game.rooms.types.mapping.RoomTile;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by SpreedBlood on 2017-12-21.
 */
public class ProcessOHFEntity {

    private boolean processEntity(RoomEntity entity, boolean isRetry, Room room) {
        boolean isPlayer = entity instanceof PlayerEntity;

        if (isPlayer && ((PlayerEntity) entity).getPlayer() == null || entity.getRoom() == null) {
            return true; // adds it to the to remove list automatically..
        }

        if (!isRetry) {
            if (isPlayer) {
                // Handle flood
                if (((PlayerEntity) entity).getPlayer().getRoomFloodTime() >= 0.5) {
                    ((PlayerEntity) entity).getPlayer().setRoomFloodTime(((PlayerEntity) entity).getPlayer().getRoomFloodTime() - 0.5);

                    if (((PlayerEntity) entity).getPlayer().getRoomFloodTime() < 0) {
                        ((PlayerEntity) entity).getPlayer().setRoomFloodTime(0);
                    }
                }
            } else {
                if (entity.getAI() != null) {
                    entity.getAI().onTick();
                }
            }

            if (entity.handItemNeedsRemove() && entity.getHandItem() != 0) {
                entity.carryItem(0);
                entity.setHandItemTimer(0);
            }

            // Handle signs
            if (entity.hasStatus(RoomEntityStatus.SIGN) && !entity.isDisplayingSign()) {
                entity.removeStatus(RoomEntityStatus.SIGN);
                entity.markNeedsUpdate();
            }

            if (entity instanceof PlayerEntity && entity.isIdleAndIncrement() && entity.isVisible()) {
                if (entity.getIdleTime() >= 60 * OrionSettings.roomIdleMinutes * 2) {
                    if (room.getData().getOwnerId() != ((PlayerEntity) entity).getPlayerId())
                        return true;
                }
            }
        }

        if (entity.hasStatus(RoomEntityStatus.MOVE)) {
            entity.removeStatus(RoomEntityStatus.MOVE);
            entity.markNeedsUpdate();
        }

        if (entity.isOHFWalking()) {
            SquarePoint squarePoint = DreamPathFinder.getNextStep(entity.getPosition().getX(), entity.getPosition().getY(), entity.getWalkingGoal().getX(), entity.getWalkingGoal().getY(), room.getMapping().getModel().getOhfGameMap(), room.getMapping(), room.getMapping().getModel().getSizeX(), room.getMapping().getModel().getSizeY(), false, room.hasAttribute("disableDiagonal"));

            if (squarePoint.getX() == entity.getPosition().getX() && squarePoint.getY() == entity.getPosition().getY()) {
                entity.setOHFWalking(false);
                if (entity.hasStatus(RoomEntityStatus.MOVE)) {
                    entity.removeStatus(RoomEntityStatus.MOVE);
                    entity.markNeedsUpdate();
                }
                return true;
            }

            if (isPlayer && ((PlayerEntity) entity).isKicked()) {

                if (((PlayerEntity) entity).getKickWalkStage() > 5) {
                    return true;
                }

                ((PlayerEntity) entity).increaseKickWalkStage();
            }

            boolean isLastStep = (
                            entity.getPosition().getX() - 1 == squarePoint.getX() &&
                            entity.getPosition().getX() + 1 == squarePoint.getX() &&
                            entity.getPosition().getY() == squarePoint.getY() &&
                            entity.getPosition().getY() == squarePoint.getY()
                    );

            if (entity.getRoom().getMapping().isValidEntityStep(entity, entity.getPosition(), new Position(squarePoint.getX(), squarePoint.getY(), 0), isLastStep) || entity.isOverriden()) {
                Position currentPos = entity.getPosition() != null ? entity.getPosition() : new Position(0, 0, 0);
                Position nextPos = new Position(squarePoint.getX(), squarePoint.getY());

                final double mountHeight = entity instanceof PlayerEntity && entity.getMountedEntity() != null ? 1.0 : 0;//(entity.getMountedEntity() != null) ? (((String) entity.getAttribute("transform")).startsWith("15 ") ? 1.0 : 0.5) : 0;

                final RoomTile tile = room.getMapping().getTile(squarePoint.getX(), squarePoint.getY());
                final double height = tile.getWalkHeight() + mountHeight;
                boolean isCancelled = entity.isWalkCancelled();
                boolean effectNeedsRemove = true;

                List<RoomItemFloor> preItems = room.getItems().getItemsOnSquare(squarePoint.getX(), squarePoint.getY());

                for (RoomItemFloor item : preItems) {
                    if (item != null) {
                        if (entity.getCurrentEffect() != null && entity.getCurrentEffect().getEffectId() == item.getDefinition().getEffectId()) {
                            if (item.getId() == tile.getTopItem()) {
                                effectNeedsRemove = false;
                            }
                        }

                        if (item.isMovementCancelled(entity)) {
                            isCancelled = true;
                        }

                        if (!isCancelled) {
                            item.onEntityPreStepOn(entity);
                        }
                    }
                }

                if (effectNeedsRemove && entity.getCurrentEffect() != null && entity.getCurrentEffect().isItemEffect()) {
                    entity.applyEffect(entity.getLastEffect());
                }

                if (room.getEntities().positionHasEntity(nextPos)) {
                    final boolean allowWalkthrough = room.getData().getAllowWalkthrough();
                    final boolean isFinalStep = entity.getWalkingGoal().equals(nextPos);

                    if (isFinalStep && allowWalkthrough) {
                        isCancelled = true;
                    } else if (!allowWalkthrough) {
                        isCancelled = true;
                    }

                    RoomEntity entityOnTile = room.getMapping().getTile(nextPos.getX(), nextPos.getY()).getEntity();

                    if (entityOnTile != null && entityOnTile.getMountedEntity() != null && entityOnTile.getMountedEntity() == entity) {
                        isCancelled = false;
                    }
                }

                if (!isCancelled) {
                    entity.setBodyRotation(Position.calculateRotation(currentPos.getX(), currentPos.getY(), squarePoint.getX(), squarePoint.getY(), entity.isMoonwalking()));
                    entity.setHeadRotation(entity.getBodyRotation());

                    entity.addStatus(RoomEntityStatus.MOVE, String.valueOf(squarePoint.getX()).concat(",").concat(String.valueOf(squarePoint.getY())).concat(",").concat(String.valueOf(height)));

                    entity.removeStatus(RoomEntityStatus.SIT);
                    entity.removeStatus(RoomEntityStatus.LAY);

                    final Position newPosition = new Position(squarePoint.getX(), squarePoint.getY(), height);
                    entity.updateAndSetPosition(newPosition);
                    entity.markNeedsUpdate();

                    if (entity instanceof PlayerEntity && entity.getMountedEntity() != null) {
                        RoomEntity mountedEntity = entity.getMountedEntity();

                        mountedEntity.moveTo(newPosition.getX(), newPosition.getY());
                    }

                    List<RoomItemFloor> postItems = room.getItems().getItemsOnSquare(squarePoint.getX(), squarePoint.getY());

                    for (RoomItemFloor item : postItems) {
                        if (item != null) {
                            item.onEntityPostStepOn(entity);
                        }
                    }

                    tile.getEntities().add(entity);
                } else {
                    if (entity.getWalkingPath() != null) {
                        entity.getWalkingPath().clear();
                    }
                    entity.getProcessingPath().clear();
                    entity.setWalkCancelled(false);//
                }
            } else {
                // RoomTile is blocked, let's try again!
                entity.moveTo(entity.getWalkingGoal().getX(), entity.getWalkingGoal().getY());
                return this.processEntity(entity, true, room);
            }
        } else {
            if (isPlayer && ((PlayerEntity) entity).isKicked())
                return true;
        }

        // Handle expiring effects
        if (entity.getCurrentEffect() != null) {
            entity.getCurrentEffect().decrementDuration();

            if (entity.getCurrentEffect().getDuration() == 0 && entity.getCurrentEffect().expires()) {
                entity.applyEffect(entity.getLastEffect() != null ? entity.getLastEffect() : null);

                if (entity.getLastEffect() != null)
                    entity.setLastEffect(null);
            }
        }

        if (entity.isWalkCancelled()) {
            entity.setWalkCancelled(false);
        }
        //
        //        if(entity.getPendingWalk() != null) {
        //            entity.moveTo(entity.getPendingWalk());
        //            entity.setPendingWalk(null);
        //        }
        //
        return false;
    }
}
