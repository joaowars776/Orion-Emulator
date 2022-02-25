package com.orionemu.server.game.polls.types.questions;

import com.orionemu.server.game.polls.types.PollQuestion;

public class WordedPollQuestion extends PollQuestion {
    public WordedPollQuestion(String question) {
        super(question);
    }

    @Override
    public int getType() {
        return 3;
    }

}
