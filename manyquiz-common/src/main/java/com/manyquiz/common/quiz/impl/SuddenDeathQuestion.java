package com.manyquiz.common.quiz.impl;

import com.manyquiz.common.quiz.model.IQuestion;
import com.manyquiz.common.quiz.model.IQuizControl;

public class SuddenDeathQuestion extends QuestionControlBase {

    private final IQuizControl quiz;

    SuddenDeathQuestion(IQuizControl quiz, IQuestion question) {
        super(question);
        this.quiz = quiz;
    }

    @Override
    public boolean canNavigateForward() {
        if (quiz.isGameOver()) return false;
        return isCorrectlyAnswered();
    }

    @Override
    public boolean canNavigateBackward() {
        return false;
    }
}
