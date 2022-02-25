package com.orionemu.server.network.messages.outgoing.landing;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

/**
 * Created by Emrik on 2017-08-05.
 */
public class HotelViewGoalComposer extends MessageComposer {

    private final boolean achieved;
    private final int personalContributionScore;
    private final int personalRank;
    private final int totalAmount;
    private final int communityHighestAchievedLevel;
    private final int scoreRemainingUntilNextLevel;
    private final int percentCompletionTowardsNextLevel;
    private final String competitionName;
    private final int timeLeft;
    private final int[] rankData;

    public HotelViewGoalComposer(boolean achieved,
                                          int personalContributionScore,
                                          int personalRank,
                                          int totalAmount,
                                          int communityHighestAchievedLevel,
                                          int scoreRemainingUntilNextLevel,
                                          int percentCompletionTowardsNextLevel,
                                          String competitionName,
                                          int timeLeft,
                                          int[] rankData)
    {
        this.achieved = achieved;
        this.personalContributionScore = personalContributionScore;
        this.personalRank = personalRank;
        this.totalAmount = totalAmount;
        this.communityHighestAchievedLevel = communityHighestAchievedLevel;
        this.scoreRemainingUntilNextLevel = scoreRemainingUntilNextLevel;
        this.percentCompletionTowardsNextLevel = percentCompletionTowardsNextLevel;
        this.competitionName = competitionName;
        this.timeLeft = timeLeft;
        this.rankData = rankData;
    }

    @Override
    public void compose(IComposer msg) {

        msg.writeBoolean(false);
        msg.writeInt(personalContributionScore);
        msg.writeInt(personalRank);
        msg.writeInt(personalRank);
        msg.writeInt(totalAmount);
        msg.writeInt(communityHighestAchievedLevel);
        msg.writeInt(scoreRemainingUntilNextLevel);
        msg.writeString(competitionName);
        msg.writeInt(timeLeft);
        msg.writeInt(rankData.length);

    }

    @Override
    public short getId() { return Composers.HotelViewGoalComposer; }
}
