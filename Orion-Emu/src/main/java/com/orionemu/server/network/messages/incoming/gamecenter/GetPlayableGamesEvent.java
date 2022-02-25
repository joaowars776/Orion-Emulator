package com.orionemu.server.network.messages.incoming.gamecenter;

import com.orionemu.server.game.achievements.AchievementGroup;
import com.orionemu.server.game.achievements.AchievementManager;
import com.orionemu.server.game.achievements.types.AchievementType;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.gamecenter.GameCenterAccountInfoComposer;
import com.orionemu.server.network.messages.outgoing.gamecenter.GameCenterAchievementsConfigurationComposer;
import com.orionemu.server.network.messages.outgoing.gamecenter.PlayableGamesComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

import java.util.Map;

public class GetPlayableGamesEvent implements Event {
    public void handle(Session session, MessageEvent msg){
        int gameId = msg.readInt();
        Map<AchievementType, AchievementGroup> achievementGroupMap = AchievementManager.getInstance().getAchievementGroups();

        session.send(new GameCenterAccountInfoComposer(gameId));
        session.send(new PlayableGamesComposer(gameId));
        session.send(new GameCenterAchievementsConfigurationComposer(gameId));
    }
}
