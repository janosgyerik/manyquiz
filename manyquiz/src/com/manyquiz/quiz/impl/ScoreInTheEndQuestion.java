package com.manyquiz.quiz.impl;

import com.manyquiz.quiz.model.IAnswer;
import com.manyquiz.quiz.model.IAnswerControl;
import com.manyquiz.quiz.model.IQuestion;

public class ScoreInTheEndQuestion extends QuestionControlBase {

    ScoreInTheEndQuestion(IQuestion question) {
        super(question);

        answerControls.clear();
        for (IAnswer answer : question.getShuffledAnswers()) {
            answerControls.add(new ScoreInTheEndAnswer(answer, this));
        }
    }

    @Override
    public boolean isPending() {
        return ! closed;
    }

    @Override
    public boolean canGotoNext() {
        return hasSelectedAnswer();
    }

    private boolean hasSelectedAnswer() {
        for (IAnswerControl answer : answerControls) {
            if (answer.isSelected()) {
                return true;
            }
        }
        return false;
    }
}
