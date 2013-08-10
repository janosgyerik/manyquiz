package com.manyquiz.quiz.impl;

import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.model.IQuestionControl;

import java.util.List;

public class SuddenDeathQuiz extends QuizControlBase {

    public SuddenDeathQuiz(List<IQuestion> questions) {
        super(questions);
    }

    @Override
    public IQuestionControl createQuestionControl(IQuestion question) {
        return new SuddenDeathQuestion(this, question);
    }

    @Override
    public boolean isGameOver() {
        for (IQuestionControl question : questionControls) {
            if (question.isPending()) {
                return false;
            }
            // TODO: score = 0 means wrong answer. This works, but it's not great
            if (question.getScore() == 0) {
                return true;
            }
        }
        return true;
    }
}
