package com.manyquiz.quiz.impl;

import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.model.IQuestionControl;

import java.util.List;

public class ScoreAsYouGoQuiz extends QuizControlBase {

    public ScoreAsYouGoQuiz(List<IQuestion> questions) {
        super(questions);
    }

    @Override
    public IQuestionControl createQuestionControl(IQuestion question) {
        return new ScoreAsYouGoQuestion(question);
    }
}
