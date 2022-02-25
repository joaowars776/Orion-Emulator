package com.orionemu.server.network.messages.incoming.polls;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.polls.PollManager;
import com.orionemu.server.game.polls.types.Poll;
import com.orionemu.server.game.polls.types.PollQuestion;
import com.orionemu.server.game.polls.types.questions.MultipleChoiceQuestion;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.polls.QuickPollVoteMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.storage.queries.polls.PollDao;
import com.orionemu.server.utilities.JsonUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class SubmitPollAnswerMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final int pollId = msg.readInt();
        final int questionId = msg.readInt();
        final int count = msg.readInt();

        if (questionId == -1) {
            if (client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null)
                return;

            Room room = client.getPlayer().getEntity().getRoom();

            if (room.getQuestion() == null) {
                return;
            }

            if (room.getYesVotes().contains(client.getPlayer().getId()) || room.getNoVotes().contains(client.getPlayer().getId())) {
                return;
            }

            String answer = msg.readString();

            if (answer.equals("1")) {
                room.getYesVotes().add(client.getPlayer().getId());
            } else {
                room.getNoVotes().add(client.getPlayer().getId());
            }

            room.getEntities().broadcastMessage(new QuickPollVoteMessageComposer(client.getPlayer().getId(), answer, room.getYesVotes().size(), room.getNoVotes().size()));
            return;
        }

        Poll poll = PollManager.getInstance().getPollbyId(pollId);

        if (poll != null) {
            PollQuestion pollQuestion = poll.getPollQuestions().get(questionId);

            if (pollQuestion == null) {
                return;
            }

            String answer;

            if (pollQuestion instanceof MultipleChoiceQuestion) {
                try {
                    final List<String> answers = Lists.newArrayList();

                    for (int i = 0; i < count; i++) {
                        final String answerStr = msg.readString();

                        if (!StringUtils.isNumeric(answerStr)) continue;

                        final int answerIndex = Integer.parseInt(answerStr);

                        answers.add(((MultipleChoiceQuestion) pollQuestion).getChoices().get(answerIndex));
                    }

                    answer = JsonUtil.getInstance().toJson(answers);
                } catch (Exception e) {
                    client.send(new AlertMessageComposer(Locale.getOrDefault("polls.invalid.answer", "Invalid answer!")));
                    return;
                }
            } else {
                answer = msg.readString();
            }

            if (PollDao.hasAnswered(client.getPlayer().getId(), pollId, questionId)) {
                return;
            }

            if(poll.isFinalQuestion(questionId)) {
                poll.onPlayerFinishedPoll(client.getPlayer());
            }

            PollDao.saveAnswer(client.getPlayer().getId(), pollId, questionId, answer);
        }
    }
}
