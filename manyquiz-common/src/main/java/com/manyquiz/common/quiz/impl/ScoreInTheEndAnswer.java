package com.manyquiz.common.quiz.impl;

import com.manyquiz.common.quiz.model.IAnswer;
import com.manyquiz.common.quiz.model.IAnswerControl;
import com.manyquiz.common.quiz.model.IQuestionControl;

public class ScoreInTheEndAnswer extends AnswerControl {

    private final IQuestionControl question;

    public ScoreInTheEndAnswer(IAnswer answer, IQuestionControl question) {
        super(answer);
        this.question = question;
    }

    @Override
    public void select() {
        for (IAnswerControl answer : question.getAnswerControls()) {
            answer.deselect();
        }
        super.select();
    }
}
