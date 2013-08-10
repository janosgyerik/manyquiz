package com.manyquiz.quiz.impl;

import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.model.IQuestionControl;

import java.util.List;

public class ScoreInTheEndQuiz extends QuizControlBase {

    public ScoreInTheEndQuiz(List<IQuestion> questions) {
        super(questions);
    }

    @Override
    public IQuestionControl createQuestionControl(IQuestion question) {
        return new ScoreInTheEndQuestion(question);
    }

    @Override
    public boolean readyToEndGame() {
        for (IQuestionControl question : questionControls) {
            if (! question.canGotoNext()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void endGame() {
        for (IQuestionControl question : questionControls) {
            question.close();
        }
    }
}
