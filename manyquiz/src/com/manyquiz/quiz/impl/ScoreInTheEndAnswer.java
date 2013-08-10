package com.manyquiz.quiz.impl;

import com.manyquiz.quiz.model.IAnswer;
import com.manyquiz.quiz.model.IAnswerControl;
import com.manyquiz.quiz.model.IQuestionControl;

public class ScoreInTheEndAnswer extends AnswerControl {

    private final IQuestionControl question;

    public ScoreInTheEndAnswer(IAnswer answer, IQuestionControl question) {
        super(answer);
        this.question = question;
    }

    @Override
    public void select() {
        for (IAnswerControl answer : question.getAnswerControls()) {
            answer.unselect();
        }
        super.select();
    }
}
