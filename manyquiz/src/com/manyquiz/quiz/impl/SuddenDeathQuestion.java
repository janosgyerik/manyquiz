package com.manyquiz.quiz.impl;

import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.model.IQuizControl;

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
