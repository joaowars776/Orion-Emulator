package com.orionemu.server.game.rooms.objects.items.types.floor.football;

import com.orionemu.server.game.achievements.types.AchievementType;
import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.game.rooms.types.components.games.GameTeam;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.sessions.Session;


public class FootballGoalFloorItem extends RoomItemFloor {
    private GameTeam gameTeam;

    public FootballGoalFloorItem(long id, int itemId, Room room, int owner, String ownerName, int x, int y, double z, int rotation, String data) {
        super(id, itemId, room, owner, ownerName, x, y, z, rotation, data);

        switch (this.getDefinition().getItemName()) {
            case "fball_goal_b":
                this.gameTeam = GameTeam.BLUE;
                break;
            case "fball_goal_r":
                this.gameTeam = GameTeam.RED;
                break;
            case "fball_goal_y":
                this.gameTeam = GameTeam.YELLOW;
                break;
            case "fball_goal_g":
                this.gameTeam = GameTeam.GREEN;
                break;
        }
    }

    @Override
    public void onItemAddedToStack(RoomItemFloor floorItem) {
        if(floorItem instanceof FootballFloorItem) {
            this.getRoom().getGame().increaseScore(this.gameTeam, 1);

            final int playerId = this.getRoom().getData().getOwnerId();

            if(PlayerManager.getInstance().isOnline(playerId)) {
                Session session = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

                if(session != null && session.getPlayer() != null && session.getPlayer().getAchievements() != null) {
                    session.getPlayer().getAchievements().progressAchievement(AchievementType.FOOTBALL_GOAL, 1);
                }
            }
        }
    }

    public GameTeam getGameTeam() {
        return gameTeam;
    }
}
