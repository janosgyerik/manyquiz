package com.manyquiz.quiz.impl;

import com.manyquiz.quiz.model.IAnswerControl;
import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.model.IQuizControl;

public class SuddenDeathQuestion extends QuestionControlBase {

    private final IQuizControl quiz;

    SuddenDeathQuestion(IQuizControl quiz, IQuestion question) {
        super(question);
        this.quiz = quiz;
    }

    @Override
    public boolean isReadyForNext() {
        if (quiz.isGameOver()) return false;
        for (IAnswerControl answerControl : answerControls) {
            if (answerControl.isSelected()) {
                if (answerControl.getAnswer().isCorrect()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isReadyForPrevious() {
        return false;
    }
}
